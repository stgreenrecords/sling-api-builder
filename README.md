📦 sling-api-builder
sling-api-builder is a modular Java project built for Adobe Experience Manager (AEM) using Apache Sling. It provides a flexible framework for dynamically building RESTful APIs using Sling servlets, resource adapters, and model annotations.

🏗 Project Modules
1. sling-api-builder-core
The heart of the framework. It includes:

MainRestServlet.java: The main Sling servlet handling REST requests.

ModelAdapter.java: Converts Sling resources into annotated models.

annotations/RestField.java: Annotation to describe fields in REST models.

binary/*: Utilities and services for file uploading and binary management.

models/BaseRestModel.java: Base class for custom API models.

restfields/*: Logic to process and serialize REST field data.

utils/*: Helpers for binary file handling, constants, and resource resolution.

2. sling-api-builder-samples
Sample implementations showcasing how to use the framework with custom models and endpoints.

3. sling-api-builder-content-demo
A content package module (content-package packaging type) meant for deploying components and configurations into an AEM instance using FileVault.

⚙️ Features
Dynamic REST servlet with automatic model resolution

Sling model-based architecture

REST field annotation system

Binary file upload support

Easily extendable with custom components

📦 Build Instructions
bash
Copy
Edit
mvn clean install
Make sure your AEM instance is running and properly configured (sling.host, sling.port, etc.) if you want to deploy content packages automatically.
