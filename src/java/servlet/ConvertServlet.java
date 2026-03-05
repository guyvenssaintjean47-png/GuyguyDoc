package servlet;

import service.ServiceDocument;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;

@MultipartConfig
@WebServlet("/ConvertServlet")
public class ConvertServlet extends HttpServlet {

    // Service responsable de la conversion des fichiers
    private ServiceDocument service = new ServiceDocument();

    // Dossier où sont stockés les fichiers convertis
    private final String uploadDir = "C:/ConvertDocs/uploads/";

    /**
     * =========================
     * GESTION DE LA CONVERSION
     * =========================
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Récupération du fichier envoyé et du type de conversion
        Part fichier = request.getPart("fichier");
        String typeConversion = request.getParameter("typeConversion");

        try {
            // Lancer la conversion (aucune limitation pour les invités)
            service.convertir(session, fichier, typeConversion);

            request.setAttribute("message", "Conversion effectuée avec succès !");

        } catch (Exception e) {
            // Message d'erreur en cas d'échec
            request.setAttribute("error", e.getMessage());
        }

       
        request.getRequestDispatcher("accueille.jsp").forward(request, response);
    }

    /**
     * =========================
     * GESTION DU TÉLÉCHARGEMENT
     * =========================
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Nom du fichier à télécharger
        String fileName = request.getParameter("file");

        if (fileName == null || fileName.isEmpty()) {
            response.getWriter().println("Fichier non spécifié !");
            return;
        }

        File file = new File(uploadDir, fileName);

        if (!file.exists()) {
            response.getWriter().println("Fichier introuvable !");
            return;
        }

        // Détermination du type MIME
        String mimeType = getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader(
            "Content-Disposition",
            "attachment; filename=\"" + file.getName() + "\""
        );
        response.setContentLengthLong(file.length());

        // Envoi du fichier au navigateur
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
