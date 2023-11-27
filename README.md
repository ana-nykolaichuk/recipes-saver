# Recipe Saving service

At the moment, users are unable to save their favourite recipes. In the scope of this project, we define a recipes model and build the system that allows to interact with them.

To build horizontally scalable service and taking into account developer experience, the technologies of choice are Docker, Java 17, Spring, jUnit, MongoDB.

## Domain Model
![](https://www.planttext.com/api/plantuml/png/NOzB2iCm34JtEeNNXTmXssxR4wYn28PQCP8SA4dlNjSswMSdpJmqcb4DnNAy0n1Ri8Uxq1GIkGsSEpd6I7K8F0lvK1YjUn9AA1WMBfQQvCdAt3mbMGklZdEyaLJDS5Pu7Y7duzn_4CObitsXLrp9jwUqmrdxLMdoc_x1zdqOzgrNWTweYvzeW97Ob-ul.png)

## System Diagram
![](https://www.planttext.com/api/plantuml/png/PP5DJyCm38Rl_XLMBvmgFeuSqBuu8349hPCu00UlCMsqD8aIRs92_7VIqh4CBf7jztdjn9b4eI2xpa3SQkifK8UTjdD3CJvh9IrUtrnbck9tsXbPyuTCZMvinrQmJWU7Z3BQiXmyemlIEW2gnGKivaRt-EMhNRgDDenJxmkaY3y2W7MAiPXHlMMhiEAmvr3W9o3MhlFEzkwNe_Vf0H_fmE6jxv2oSsh5jVOS1t6Cpu6AzgmmnuRAYUq7bEKT_g_y0VCKow2RLZBR5y3uxhBtfXwtkBglrW0Nk22XqvqYg9HlAAOTB9rjt68sR6UC0Nvdf3x7uldmJ819sc7ws6y0)

## Use cases
### Recipes service
- Operator creates a recipe
- Operator updates a recipe
- Operator removes a recipe
### Saved recipes service
- User saves a recipe
- User retrieves all saved recipes, e.g. for "My recipes" page implementation
- User filters saved recipes, e.g. search implementation on the "My recipes" page
- User removes a recipe from favourites

## Deployment instructions
TBD
