<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PastDoc - Convertisseur de documents simple et efficace</title>

    <style>
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: "Segoe UI", "Inter", Arial, sans-serif;
            min-height: 100vh;
            background-image: linear-gradient(
                rgba(0, 0, 0, 0.65),
                rgba(0, 0, 0, 0.65)
            ),
            url("image/Backgroung2.jpg");
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            width: 90%;
            max-width: 900px;
            padding: 50px 45px;
            background: rgba(20, 20, 20, 0.75);
            backdrop-filter: blur(8px);
            border-radius: 16px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.5);
            text-align: center;
            color: #ffffff;
        }

        h1 {
            font-size: 48px;
            margin-bottom: 12px;
        }

        .subtitle {
            font-size: 18px;
            color: #dcdcdc;
            margin-bottom: 25px;
        }

        .description {
            font-size: 16px;
            line-height: 1.7;
            margin-bottom: 35px;
        }

        .description strong {
            color: #FFB703;
        }

        form {
            margin-bottom: 30px;
        }

        input[type="file"],
        select {
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            border-radius: 8px;
            border: none;
            font-size: 15px;
        }

        .btn {
            padding: 15px 42px;
            font-size: 18px;
            font-weight: 600;
            color: #1a1a1a;
            background: linear-gradient(135deg, #FFB703, #F4A261);
            border-radius: 50px;
            border: none;
            cursor: pointer;
            box-shadow: 0 10px 25px rgba(255, 183, 3, 0.45);
            transition: all 0.3s ease;
        }

        .btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 30px rgba(255, 183, 3, 0.65);
        }

        .message {
            margin-top: 20px;
            color: #7CFC98;
            font-weight: 600;
        }

        .error {
            margin-top: 20px;
            color: #FF6B6B;
            font-weight: 600;
        }

        footer {
            margin-top: 35px;
            font-size: 13px;
            color: #b5b5b5;
        }
    </style>
</head>

<body>
<div class="container">
    <h1>PastDoc</h1>
    <p class="subtitle">Le meilleur convertisseur de documents en ligne</p>

    <p class="description">
        Convertissez vos fichiers en un clic :
        <strong>Word → PDF</strong>,
        <strong>PDF → Word</strong>,
        <strong>PDF → Excel</strong>.
        <br>
        Rapide, simple et sécurisé.
    </p>

    <form action="ConvertServlet" method="post" enctype="multipart/form-data">
        <input type="file" name="fichier" required>

        <select name="typeConversion" required>
            <option value="">-- Choisir la conversion --</option>
            <option value="wordToPdf">Word → PDF</option>
            <option value="pdfToWord">PDF → Word</option>
            <option value="pdfToExcel">PDF → Excel</option>
        </select>

        <button type="submit" class="btn">Convertir le fichier</button>
    </form>
    
    <%
    String fichierConverti = (String) session.getAttribute("fichierConverti");
    if (fichierConverti != null) {
%>
    <a href="DownloadServlet?file=<%= fichierConverti %>" class="btn" style="margin-top:15px;">Télécharger</a>
<%
        
        session.removeAttribute("fichierConverti");
    }
%>

  </a>

    <!-- Partager WhatsApp -->
    <a target="_blank"
       class="btn btn-success"
       href="https://wa.me/?text=Voici le document converti : http://localhost:8080/ConvertDocs/DownloadServlet?file=${sessionScope.fichierConverti}">
        Partager sur WhatsApp
    </a>

    <!-- Partager Telegram -->
    <a target="_blank"
       class="btn btn-info"
       href="https://t.me/share/url?url=http://localhost:8080/ConvertDocs/DownloadServlet?file=${sessionScope.fichierConverti}&text=Document converti">
        Partager sur Telegram
    </a>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
        <div class="message"><%= message %></div>
    <%
        }
    %>

  
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="error"><%= error %></div>
    <%
        }
    %>

    <footer>
        © 2026 PastDoc – Tous droits réservés
    </footer>
</div>
</body>
</html>
