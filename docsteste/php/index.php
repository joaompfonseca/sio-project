<?php
// Register WSDL
$OKMAuth = new SoapClient(
    "http://sio-project.northeurope.cloudapp.azure.com/OpenKM/services/OKMAuth?wsdl"
);
$OKMDocument = new SoapClient(
    "http://sio-project.northeurope.cloudapp.azure.com/OpenKM/services/OKMDocument?wsdl"
);

// Check if form is submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve form data
    $username = $_POST["username"];
    $password = $_POST["password"];
    $fileName = $_POST["fileName"];

    // File upload handling
    $fileUpload = $_FILES["fileUpload"];
    $fileTmpName = $fileUpload["tmp_name"];
    $fileSize = $fileUpload["size"];

    // Login
    $loginResp = $OKMAuth->login(array(
        "user" => $username,
        "password" => $password,
    ));
    $token = $loginResp->return;

    // Check if login is successful
    if ($token) {
        // Create document
        $doc = array(
            "path" => "/okm:root/" . $fileName,
            "mimeType" => null,
            "actualVersion" => null,
            "author" => null,
            "checkedOut" => false,
            "created" => null,
            "keywords" => null,
            "language" => null,
            "lastModified" => null,
            "lockInfo" => null,
            "locked" => false,
            "permissions" => 0,
            "size" => $fileSize,
            "subscribed" => false,
            "uuid" => null,
            "convertibleToPdf" => false,
            "convertibleToSwf" => false,
            "compactable" => false,
            "training" => false,
            "convertibleToDxf" => false, 
            'signed' => false, 
            'cipherName' => null, 
            'subscriptors'=>null, 
            'title'=>null, 
            'description'=>null, 
            'categories'=>null, 
            'notes'=>null,
        );

        // Read file content
        $fileContent = file_get_contents($fileTmpName);

        // Upload document
        $createResp = $OKMDocument->create(array(
            "token" => $token,
            "doc" => $doc,
            "content" => $fileContent,
        ));

        $newDoc = $createResp->return;

        $response = [
            "success" => true,
            "message" => "Document uploaded successfully",
            "documentPath" => $newDoc->path,
            "documentAuthor" => $newDoc->author,
            "documentSize" => $newDoc->actualVersion->size,
        ];

        echo json_encode($response);

        $OKMAuth->logout($token);
    } else {
        echo json_encode(["success" => false, "message" => "Invalid credentials"]);
    }
} else {
    echo json_encode(["success" => false, "message" => "Invalid request"]);
}
?>
