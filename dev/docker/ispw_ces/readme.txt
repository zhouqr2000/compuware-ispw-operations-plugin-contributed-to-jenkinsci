#build docker image with CES installed
docker build -t sam/ces .

#start a docker container
docker run --name sam_ces_1 -tid -p 48080:48080 -p 1545:1545 sam/ces
