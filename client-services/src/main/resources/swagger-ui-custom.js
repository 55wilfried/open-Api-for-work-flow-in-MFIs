window.onload = function() {
  const ui = SwaggerUIBundle({
    url: "/v3/api-docs",
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl,
      {
        requestInterceptor: (req) => {
          if (req.headers && req.headers['Authorization'] &&
              !req.headers['Authorization'].startsWith('Bearer ')) {
            req.headers['Authorization'] = 'Bearer ' + req.headers['Authorization'];
          }
          return req;
        }
      }
    ],
    layout: "StandaloneLayout"
  });
}
