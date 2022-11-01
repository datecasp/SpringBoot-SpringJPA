# Spring Boot Web API REST example  (WIP)

Ejemplo de uso del framework Spring Boot Data JPA para el desarrollo de una web

API REST para la gestión de una Base de Datos.

Simula el gestor de datos de una academía donde manejan datos de cursos ofrecidos,

alumnos apuntados a algún curso y asignaturas que se imparten, pudiendo realizar

operaciones CRUD para las diferentes entidades. Las diefrentes relaciones entre

las entidades así como las operaciones están explicadas más adelante.

[ Tecnologías usadas : ]

- IntelliJ 2022.2
- Plugin de Maven versión 2.5.5
- Spring Boot 
- Java 17
- Swagger 2 para documentación de la API
- MSSQL server / H2
- Dependencias Maven:
  * spring-boot-starter-data-jpa
  * spring-boot-starter-web
  * spring-boot-devtools
  * h2
  * spring-boot-starter-test
  * springfox-boot-starter -> para el uso de Swagger
  * mssql-jdbc -> para MSSQL -- Config en application.properties 

     * Si no se configura MSSQL se usa H2 por defecto

[ Entidades y Relaciones : ]

Se generan tres entidades para los tres grupos de elementos de datos que se van

a manejar en la aplicación: `Alumnos`, `Cursos` y `Asignaturas`, indicadas con

la *anotación* @Entity. Se definen en sus respectivas clases en el subpaquete

`entities` y sus interfaces, que deben extender JpaRepository<>, desarrolladas

en el subpaquete `repositories`, para desacoplar el código.

Además, el subpàquete `controllers` incluye los controladores, indicados con la 

*anotación* @RestController, de la API para cada una de estas entidades. De momento

son los que manejan la lógica de la aplicación. Está agendada la creación de una

capa de abstracción por medio de la implementación de `servicios` para cada

controlador.

Las relaciones establecidas entre las entidades de la son:

- 1 a N entre Curso y Alumno. Un curso tiene muchos alumnos y un alumno sólo

    puede estar apuntado a un curso. Esta relación se genera en Spring mediante

    un atributo Curso curso en la entidad Alumno, generando una columna con el 

    id del curso como clave foránea, que lo vincula a un curso concreto. Para esto

    se usan *anotaciones*.
- N a M entre Asignatura y Curso. Una asignatura puede ser impartida en varios 
    
    cursos y un curso puede tener varias asignaturas. Spring usa el concepto de 

    parte *dueña* y parte *inversa* en este tipo de relaciones, siendo en este 

    caso Curso la parte *dueña* y Asignatura la parte *inversa*. Para ello se usan
    
    *anotaciones*. Con esta configuración, Spring genera una entidad `curso_asignaturas`

    con columnas cursoID y asignaturaId recibidas desde las respectivas entidades,

    lo que genera un registro persistente de estas relaciones.

Además se incluyen otras clases de ayuda y estructuración del código, subpaquete

`helpers` y otro de configuración para la documentación en Swagger, `SwaggerConfig`

dentro del subpaquete `config`.

[ Agendado : ]

- Creación de capa de abstracción mediante servicios
- Implementación de capa de seguridad (probablemente con JWT)
- Desarrollo de UI
- Limpieza y optimización de código

 
 
        
