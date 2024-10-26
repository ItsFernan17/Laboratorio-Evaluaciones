# Laboratorio-Evaluaciones-Web

<img src="./Aplicación/Laboratorio de Evaluaciones MINDEF/web/resources/img/logotipo.png alt="Laboratorio Evaluaciones" width="350" />

## Descripción

Este es el repositorio del sistema **Laboratorio de Evaluaciones Web**, una plataforma diseñada para gestionar exámenes y asignaciones de evaluaciones. La aplicación fue creada utilizando **Hibernate** para la persistencia de datos, **POJOs**, **XHTML**, y **PrimeFaces** para el desarrollo de la interfaz. Además, se empleó **iReport** para la generación de reportes, como el registro de usuarios, certificaciones de exámenes, y reportes de exámenes.

## Características

- **Tecnologías Backend**: Hibernate, JDK 7 y 8
- **Frontend**: XHTML + PrimeFaces
- **Reportes**: Generación de PDF de constancias y otros reportes con iReport
- **Gestión de exámenes, asignaciones y evaluados**

## Requisitos previos

Asegúrate de tener instalados los siguientes requisitos en tu entorno:

- **JDK** 7 y 8
- **NetBeans** 8.2
- **MySQL Workbench** (para configurar la base de datos)

## Instalación

Sigue estos pasos para instalar y configurar el proyecto en tu entorno local:

1. Clona este repositorio:

   ```bash
   git clone https://github.com/tu-usuario/laboratorio-evaluaciones-web.git

2. Configura la base de datos:

- Entra a la carpeta documentacion y abre el archivo de MySQL Workbench llamado Modelo Entidad Relacional - Final.
- Utiliza la opción de Forward Engineering para crear la base de datos en MySQL.
- Carga el archivo inserciones de datos.sql para incluir los datos necesarios, procedimientos almacenados, y datos de prueba.

3. Abre el proyecto en NetBeans 8.2 y ejecútalo en el servidor web integrado de NetBeans para iniciar la aplicación.