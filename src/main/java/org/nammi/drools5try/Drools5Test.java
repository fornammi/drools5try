package org.nammi.drools5try;

import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class Drools5Test {
	public static void main(String[] args) throws Exception{
		final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		Resource rs = ResourceFactory.newClassPathResource("drl/addPoint.drl", PointDomain.class);
		kbuilder.add(rs, ResourceType.DRL);
		//kbuilder.add(ResourceFactory.newClassPathResource("drl/addPoint.drl", PointDomain.class), ResourceType.DRL);
		if(kbuilder.hasErrors()){
			System.out.println(kbuilder.getErrors().toString());
			throw new RuntimeException("Unable to compile \"addPoint.drl\".");
		}
		final Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
		final KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(pkgs);
		final StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		
		final PointDomain pointDomain = new PointDomain();
		pointDomain.setUserName("nammi");
		pointDomain.setBackMoney(100d);
		pointDomain.setBuyMoney(500d);
		pointDomain.setBackNums(1);
		pointDomain.setBuyNums(5);
		pointDomain.setBillThisMonth(5);
		pointDomain.setBirthDay(true);
		pointDomain.setPoint(0l);
		
		ksession.insert(pointDomain);
		ksession.fireAllRules();
		ksession.dispose();
		
		System.out.println("执行完毕BillThisMonth："+pointDomain.getBillThisMonth());  
        System.out.println("执行完毕BuyMoney："+pointDomain.getBuyMoney());  
        System.out.println("执行完毕BuyNums："+pointDomain.getBuyNums());
        System.out.println("执行完毕规则引擎决定发送积分："+pointDomain.getPoint());
	}
	/*PointRuleEngine pointRuleEngine = new PointRuleEngineImpl();
	while(true){
		InputStream is = System.in;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String input = br.readLine();
		if(null!=input && "i".equals(input)){
			System.out.println("初始化规则引擎...");  
            pointRuleEngine.initEngine();
            System.out.println("初始化规则引擎结束.");  
		}else if("e".equals(input)){
			final PointDomain pointDomain = new PointDomain();
			pointDomain.setUserName("nammi");
			pointDomain.setBackMoney(100d);
			pointDomain.setBuyMoney(500d);
			pointDomain.setBackNums(1);
			pointDomain.setBuyNums(5);
			pointDomain.setBillThisMonth(5);
			pointDomain.setBirthDay(true);
			pointDomain.setPoint(0l);
			pointRuleEngine.executeRuleEngine(pointDomain);
			
			System.out.println("执行完毕BillThisMonth："+pointDomain.getBillThisMonth());  
            System.out.println("执行完毕BuyMoney："+pointDomain.getBuyMoney());  
            System.out.println("执行完毕BuyNums："+pointDomain.getBuyNums());
            System.out.println("执行完毕规则引擎决定发送积分："+pointDomain.getPoint());
		}else if("r".equals(input)){
			System.out.println("刷新规则文件...");
            pointRuleEngine.refreshEngineRule();
            System.out.println("刷新规则文件结束.");
		}
	}*/
}