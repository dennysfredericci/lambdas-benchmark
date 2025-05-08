
mvn archetype:generate \
-DarchetypeGroupId=software.amazon.awssdk \
-DarchetypeArtifactId=archetype-lambda \
-DarchetypeVersion=2.15.79 \
-DgroupId=com.devoctans.lambda \
-DartifactId=vanilla-java \
-Dservice=dynamodb  \
-Dregion=eu-west-1 \
-DinteractiveMode=false


refazer cloud formation com serveless http api 

https://github.com/aws-samples/sessions-with-aws-sam/blob/78ebaa71baadf092e13e635c1a0e89e7c49f802f/sam-or-cdk/sam/template.yaml#L201

servless function, etc