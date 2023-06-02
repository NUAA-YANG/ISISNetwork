<Information>
    <type>router</type><!--描述为路由器router-->
    <manufacture>${manufacture}</manufacture><!--厂商-->
    <lxdName>${lxdName}</lxdName>
    <#if netInterfacesList?? && (netInterfacesList?size>0)>
    <interfaces><!--描述接口信息-->
        <#list netInterfacesList as netInterfaces>
        <interface><!--描述详细接口信息-->
            <name>${netInterfaces.name}</name>
            <ipAddress>${netInterfaces.ipAddress}</ipAddress>
            <subnetMask>${netInterfaces.subnetMask}</subnetMask>
        </interface>
        </#list>
    </interfaces>
    </#if>
    <protocol><!--描述协议信息-->
        <#if isisFlag == "yes">
        <isis>
            <#if flag??>
            <flag>${flag}</flag><!--isis编号-->
            </#if>
            <#if type??>
            <type>${type}</type><!--isis协议等级-->
            </#if>
            <#if net??>
            <net>${net}</net><!--isis协议net号-->
            </#if>
            <#if ethName??>
            <ethName>${ethName}</ethName><!--isis协议网卡的名称-->
            </#if>
        </isis>
        </#if>
    </protocol>
</Information>