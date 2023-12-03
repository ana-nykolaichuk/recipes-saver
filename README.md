# Recipe Saving service

At the moment, users are unable to save their favourite recipes. In the scope of this project, we define a recipes model and build the system that allows to interact with them.

To build horizontally scalable service and make a production-ready deployable image, the technologies of choice are Docker, Java 17, Spring, jUnit, MongoDB. Open API 3 provides the UI. 

## Domain Model
![](https://www.planttext.com/api/plantuml/png/NK-x3i8m3Dpz5PwH-WSCPMJb3qnhLP686paEKeNuT_82HMpdk_ETFKSZjUGVm6BE3hpnuWBZ2n1FAEIv0YURykgo9zOvA0TIChTBrg9fMWfkOcHzveM8aloTjNBQOess26_ubbr_iV-5vFSaDbWlbUYshXPeH-1YwtUVfsb8XQ6WA2q4O6PPyyyV)

## System Diagram
![](https://www.planttext.com/api/plantuml/png/JP2zRW9138HxFuLr_Iaa9GNY9vGqW4IHf32xpc5brxlQDOWeohldBbmY6akUcKyZpRmQ5Jl70FL3D5EXY55q5QZMWtWxuUFJvCxp_4xdO7k-sY98fv7LqFM7orqgYB9zPiPKx9G0o5agsAo23F67LzsaemJ6HSuDKiMR0Q39CpPRTjApExg8TWr-0Q9BCISTu9VHhLmkulXjm6zIXM-0iQ9jszcVFCNjUhS7y6HqfDeNR99swNc9lztZ0p36SK3_nJchxzVv0G00)

## Use cases
### Recipes service
- User creates a recipe
- User updates a recipe
- User removes a recipe
- User retrieves all saved recipes, e.g. for "My recipes" page implementation
- User filters saved recipes, e.g. search implementation on the "My recipes" page

## Deployment instructions
To locally roll out the service and all the associated containers run the next commands: 
1. `mvn package` to assemble the `jar` to be deployed
2. `docker compose up --build` to deploy the service and associated images

You can reach the service on `localhost:8080` and use it via curl or postman. 
Alternatively, you can use the service via graphic UI on `http://localhost:8080/swagger-ui/index.html`
