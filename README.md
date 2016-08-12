# instagramDemo
Demostración de integración entre una aplicación de Android e Instagram

Se utiliza las siguientes librerías:

- [instagramapi](https://github.com/sayyam/instagramapi)
- [picasso](https://github.com/square/picasso)


La aplicación hace lo básico, lista el perfil y permite buscar un determinado tag 

Para el correcto funcionamiento del demo, es necesario agregar sus propias credenciales en los archivos de values

```xml
    <?xml version="1.0" encoding="utf-8"?>
    <resources>
        <string name="client_id" translatable="false">....</string>
        <string name="callback_url" translatable="false">....</string>
    </resources>
```

Estas credenciales las puedes obtener desde [https://www.instagram.com/developer/](https://www.instagram.com/developer/)
