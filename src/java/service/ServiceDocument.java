package service;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import com.convertapi.client.ConversionResult;
import com.convertapi.client.Config;

import java.util.concurrent.CompletableFuture;

public class ServiceDocument {

    // Dossier de stockage temporaire
    private final String uploadDir = "C:/ConvertDocs/uploads/";

    // Configuration ConvertAPI
    private final Config convertApiConfig;

    public ServiceDocument() {
        this.convertApiConfig =
            Config.defaults("PW2r7wZYB5PxcslPvcw55bSUvtIENHAM");
    }

    /**
     * =========================
     * MÉTHODE PRINCIPALE
     * =========================
     */
    public File convertir(HttpSession session, Part fichier, String typeConversion) throws Exception {

        if (fichier == null || fichier.getSize() == 0) {
            throw new Exception("Aucun fichier sélectionné.");
        }

        String nomOriginal = fichier.getSubmittedFileName();
        String extensionFichier = getExtension(nomOriginal);

        String typeAvant;
        String typeApres;

        // 🔁 INTERPRÉTATION DU TYPE DE CONVERSION
        switch (typeConversion) {
            case "wordToPdf":
                typeAvant = "docx";
                typeApres = "pdf";
                break;

            case "pdfToWord":
                typeAvant = "pdf";
                typeApres = "docx";
                break;

            case "pdfToExcel":
                typeAvant = "pdf";
                typeApres = "xlsx";
                break;

            default:
                throw new Exception("Type de conversion invalide.");
        }

        // Vérification cohérence fichier / conversion
        if (!extensionFichier.equals(typeAvant)) {
            throw new Exception(
                "Le fichier sélectionné ne correspond pas au type de conversion choisi."
            );
        }

        // Création du dossier si nécessaire
        File dossier = new File(uploadDir);
        if (!dossier.exists()) {
            dossier.mkdirs();
        }

        // Sauvegarde temporaire du fichier uploadé
        String nomStockage = System.currentTimeMillis() + "_" + nomOriginal;
        File fichierStocke = new File(dossier, nomStockage);

        try (InputStream in = fichier.getInputStream();
             FileOutputStream out = new FileOutputStream(fichierStocke)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        File fichierConverti;

        // 🔁 CHOIX DU MOTEUR DE CONVERSION
        if (typeAvant.equals("pdf") && typeApres.equals("docx")) {
            fichierConverti = convertPdfToWord(fichierStocke);
        } else if (typeAvant.equals("docx") && typeApres.equals("pdf")) {
            fichierConverti = convertWordToPdf(fichierStocke);
        } else if (typeAvant.equals("pdf") && typeApres.equals("xlsx")) {
            fichierConverti = convertPdfToExcel(fichierStocke);
        } else {
            throw new Exception("Conversion non supportée.");
        }

        // Stockage du nom du fichier pour téléchargement
        session.setAttribute("fichierConverti", fichierConverti.getName());

        return fichierConverti;
    }

    /**
     * =========================
     * CONVERSION PDF → WORD
     * =========================
     */
    private File convertPdfToWord(File fichierPdf) throws Exception {

        CompletableFuture<ConversionResult> future =
            ConvertApi.convert(
                "pdf",
                "docx",
                new Param[] { new Param("file", fichierPdf.toPath()) },
                convertApiConfig
            );

        ConversionResult result = future.get();
        result.saveFilesSync(fichierPdf.getParentFile().toPath());

        return new File(
            fichierPdf.getParent(),
            fichierPdf.getName().replace(".pdf", ".docx")
        );
    }

    /**
     * =========================
     * CONVERSION PDF → EXCEL
     * =========================
     */
    private File convertPdfToExcel(File fichierPdf) throws Exception {

        CompletableFuture<ConversionResult> future =
            ConvertApi.convert(
                "pdf",
                "xlsx",
                new Param[] { new Param("file", fichierPdf.toPath()) },
                convertApiConfig
            );

        ConversionResult result = future.get();
        result.saveFilesSync(fichierPdf.getParentFile().toPath());

        return new File(
            fichierPdf.getParent(),
            fichierPdf.getName().replace(".pdf", ".xlsx")
        );
    }

    /**
     * =========================
     * CONVERSION WORD → PDF
     * =========================
     */
    private File convertWordToPdf(File fichierWord) throws Exception {

        CompletableFuture<ConversionResult> future =
            ConvertApi.convert(
                "docx",
                "pdf",
                new Param[] { new Param("file", fichierWord.toPath()) },
                convertApiConfig
            );

        ConversionResult result = future.get();
        result.saveFilesSync(fichierWord.getParentFile().toPath());

        return new File(
            fichierWord.getParent(),
            fichierWord.getName().replace(".docx", ".pdf")
        );
    }

    /**
     * =========================
     * MÉTHODE UTILITAIRE
     * =========================
     */
    private String getExtension(String nomFichier) {
        int i = nomFichier.lastIndexOf('.');
        if (i > 0) {
            return nomFichier.substring(i + 1).toLowerCase();
        }
        return "";
    }
}
