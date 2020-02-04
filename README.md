![banner](https://github.com/Vintaje/LiveNetAndroidClient/blob/master/bannerlivenet.png)

Desarrollo de la APP LiveNet Client para Android

## Descripcion

Proyecto con soporte de Mapa en tiempo real para visualizar los usuarios agregados entre si en nuestro servidor REST junto con un chat paralelo con soporte en FireBase

#### Tabla de Contenido

## 1. [Dependencias](#dependencias)  
## 2. [Login y Registro](#loginregistro) 
## 3. [Mapa](#mapa) 
## 4. [Perfil de Usuario](#perfilusuario) 
## 5. [Chat](#chat) 

<a name="dependencias"></a>
## 1. Dependencias


```
    //REST
    implementation 'com.squareup.retrofit2:retrofit:2.7.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.7.0'
    implementation 'com.google.code.gson:gson:2.8.5'

    //Google Map Service
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    
    //Librerias Chat
    implementation 'com.google.firebase:firebase-auth:19.2.0'
    implementation 'com.google.firebase:firebase-core:17.2.2'
    implementation 'com.google.firebase:firebase-database:19.2.1'
    implementation 'com.google.android.gms:play-services-auth:17.0.0'
    implementation 'com.google.android.gms:play-services-analytics:17.0.0'
    apply plugin: 'com.google.gms.google-services'

    implementation('com.journeyapps:zxing-android-embedded:3.6.0') { transitive = false }
    implementation 'com.google.zxing:core:3.4.0'
```

<a name="loginregistro"></a>
## 2. Login y Registro

Registro y Login instantaneo en el servidor REST como en Firebase para poder usar el Chat
```
Necesario tener Google Play Services actualizado
```

<a name="mapa"></a>
## 3. Mapa

Mapa en tiempo real proporcionado por Google donde se muestran los amigos(foto y posicion) del usuario
```
Google Map
```

<a name="perfilusuario"></a>
## 4. Perfil de Usuario

Perfil cuyos datos los obtiene del servidor REST. Para agregar usuarios tienes la opcion de leer un QR de otro usuario o visualizar el tuyo propio


<a name="chat"></a>
## 5. Chat

Sistema de Chat en tiempo real apoyado con Firebase para obtencion de datos en todo momento

```
Usuarios agregados obtenidos del REST + Soporte de mensajes en Google FireBase
```


