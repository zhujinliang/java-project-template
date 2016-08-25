#!/bin/bash

if [ $# -lt 1 ];then
    echo "error: please input project name!"
    exit 1
fi

pj=$1
echo "create project: ${pj} start."
cp -r basic ${pj}

modules="common dao model rpc service web"
for module in ${modules}
do
    dest="${pj}/${pj}-${module}"
    echo "create module ${dest}"
    mv ${pj}/basic-${module} ${dest}
    mv ${dest}/src/main/java/com/dada/basic ${dest}/src/main/java/com/dada/${pj}
done

sed -i '' "s/{basic}/${pj}/g" `grep -rl '{basic}' ${pj}`

echo "create project: ${pj} success."
