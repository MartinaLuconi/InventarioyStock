**Sistema de Gestión de Inventario - Backend**

*MVP de manejo de inventarios, pronósticos de demanda y gestión de stock.*

**Descripción:**

Este repositorio corresponde al backend del sistema de gestión de inventarios y pronósticos de demanda.
Se desarrolló con Java utilizando el framework Spring Boot y se conecta a una base de datos MariaDB para almacenar y gestionar la información de artículos, proveedores, órdenes de compra y ventas.

Este backend provee una API REST que es consumida por el Frontend del Sistema: https://github.com/solfalabella7/frontend-inventario.git

**Funcionalidades principales:**

- API REST para el manejo de inventarios.
- CRUD de artículos y proveedores.
- Asociación de artículos con proveedores.
- Gestión de modelos de inventario (lote fijo e intervalo fijo).
- Generación automática de órdenes de compra cuando el stock es bajo.
- Gestión de ventas.
- Cálculos y verificaciones para el control de stock.


**Tecnologías utilizadas:**

1. Backend:

- Java 17+
- Spring Boot (Web, Data JPA)
- Hibernate / JPA
- Gradle

2. Base de datos:

- MariaDB

3. Herramientas adicionales:

- DBeaver (administración de BD)
- Lombok (reducción de código boilerplate)


**Requisitos previos:**

- Java 17 o superior
- Gradle
- MariaDB configurado y en ejecución


**Instalación y ejecución:**
1. Clonar el repositorio backend:
- git clone MartinaLuconi/InventarioyStock
- cd <CARPETA_BACKEND>
2. Configurar la base de datos en src/main/resources/application.properties:
- spring.datasource.url=jdbc:mariadb://localhost:3307/inventario
- spring.datasource.username=root
- spring.datasource.password=admin
3. Ejecutar el proyecto
4. La API estará disponible en:
- http://localhost:5173
