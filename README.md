````

Creating Asymmetric Key Pair


resources -> certs package ->  private.pem ve public.pem  : 

openssl -> user environment variables -> add to C:\Program Files\Git\usr\bin\ 

commant prompt inc certs directory:

- openssl genrsa -out keypair.pem 2048

- openssl rsa -in keypair.pem -pubout -out public.pem

- openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem

- delete keypair.pem

````