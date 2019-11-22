
nginx代理时那些CORS的奇技淫巧。


把Origin设成空：
```s
location / {
    proxy_pass http://localhost:8000;
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "upgrade";
    proxy_set_header Origin "";
}
```

[Blocking Cross Origin API request for ](https://github.com/jupyterhub/jupyterhub/issues/536)


