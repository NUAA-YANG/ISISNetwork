<Information>
    <type>router</type><!--描述为路由器router-->
    <lxdName>${lxdName}</lxdName>
    <#if interfaceFtlList??>
    <interfaces><!--描述接口信息-->
        <#list interfaceFtlList as interfaceFtl>
        <interface><!--描述详细接口信息-->
            <name>${interfaceFtl.name}</name>
            <ipAddress>${interfaceFtl.ipAddress}</ipAddress>
            <subnetMask>${interfaceFtl.subnetMask}</subnetMask>
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