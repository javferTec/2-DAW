- Los modelos del controlador se implementan con Record, son clases inmutables que incluyen los getters y los setters.

- La anotación @JsonProperty se utiliza para mostrar de una forma que determinamos el nombre de la propiedad en el JSON.
    - Ejemplo -> @JsonProperty("publisherUser") PublisherCollection publisherCollection,
      Se mostrará en el JSON como publisherUser en vez de publisherCollection.
