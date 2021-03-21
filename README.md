# CHALLENGE JAVA - ALKEMY LABS

### Requerimientos

Deberás crear una aplicación MVC en JAVA utilizando el framework `Spring`. El objetivo es simular una aplicación web donde los alumnos de una universidad puedan inscribirse a las materias que desean cursar.

#### Base de datos

Leyendo los requerimientos deberás armar la base de datos que consideres apropiada para que todo funcione correctamente. El tipo de base de datos debe ser relacional, no importa que sea `MySQL` o `SQL Server`. Todos los nombres de tablas, columnas, índices deben estar en **inglés** y usar **underscore** para separar palabras. Es necesario que utilices el `ORM` para acceder a la base de datos.

#### Registro
En la aplicación hay dos tipos de usuarios: `administrador` de sitio y `alumno`. 

Ambos utilizan el mismo login, especificando si son alumnos o administradores. El administrador podrá gestionar las materias, profesores, cupos de inscripción. El alumno ingresa con su DNI y legajo y podrá seleccionar las materias en las que desea inscribirse. *Tomar como premisa que no hay materias correlativas y todos los alumnos regulares se encuentran registrados en la base de datos*.

##### El usuario administrador podrá realizar las siguientes acciones:

- Gestionar los Profesores de la Universidad
  - Nombre
  - Apellido
  - DNI
  - Activo

- Gestionar las Materias a ofrecer
  - Nombre
  - Horario
  - Profesor
  - Cupo máximo de alumnos

##### El alumno podrá realizar las siguientes acciones:

- Listar todas las materias que estén disponibles
- Entrar a la materia, ver la descripción y ver la información de la misma
- Inscribirse en dicha materia.

### Frontend

Puedes llenar las vistas desde el Backend, devolviendo un modelo al View desde el controller, o puedes utilizar librerías como Jquery que realicen peticiones por AJAX a los controladores y completen las vistas con la información recibida.

### Rutas y Seguridad
Si un usuario no autenticado intenta acceder a alguna url de la plataforma, deberá ser redirigido al login.

### Criterios a Evaluar

- Diseño responsive, moderno, intuitivo.
  - Puede ser algo minimalista, sencillo, pero funcional
  - Se puede usar cualquier framework CSS: Bootstrap, Materialize, Bulma
- Conocimientos generales de Java
- Conocimientos básicos / intermedios de Jquery
- Correcto uso de los controllers
- Correcto uso de los modelos, relaciones, atributos
- Validación de todos los formularios
- Seguridad
  - Que usuarios alumnos no ingresen a rutas de usuarios administradores
- Buenas prácticas de codificación
- Correcto diseño de la base de datos
- Optimización de las tablas

### Bonus

Se requiere que implementes al menos uno de estos puntos (a elección)

- En listado de materias
  - Que aparezcan en orden alfabético
  - Mostrar la cantidad de cupos que quedan para inscribirse en cada una
- En la inscripción de materias
  - No permitir que un alumno se inscriba en dos materiales que se dictan en el mismo horario
