# ticketing

<h2>Install nginx ingress on GKE:</h2>

<p>GKE has helm installed already</p>
<p>helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx</p>
<p>helm repo update</p>

<p>Since we are deploying nginx ingress using helm charts, we have to create a values.yamlfile 
to override the default configuration. let’s create it values.yamland keep the below content in this.</p>

<strong>Deploying Nginx ingress controller with default ingress class nginxand service type ClusterIP with NEG annotation</strong>

<p>helm upgrade --install -f values.yaml ingress-nginx ingress-nginx --repo https://kubernetes.github.io/ingress-nginx --namespace ingress-nginx --create-namespace</p>
<p>Command output should look like this:</p>
</br>
<img src="./document/ingress-example1.webp" style="width: 1000px; height: 700px">
