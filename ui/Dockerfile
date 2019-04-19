FROM node:8.9 as node

WORKDIR /app

COPY package.json /app/

RUN npm install
COPY ./ /app/

RUN npm run build -- --prod

FROM nginx:1.15

COPY --from=node /app/dist/ /usr/share/nginx/html

COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
