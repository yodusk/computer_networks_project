cd backend && ./gradlew build -x test && cd ..
docker-compose down
docker-compose build
docker-compose up
