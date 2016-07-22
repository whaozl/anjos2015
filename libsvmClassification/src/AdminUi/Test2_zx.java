package AdminUi;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class Test2_zx {
	public static void main(String[] args) {
		
		/*
		 * 测试数据  a={2.0 3.0}  1.0   b={1.0 2.0} -1.0
		 */
		svm_node pa1=new svm_node();
		pa1.index=1;
		pa1.value=2.0;
		svm_node pa2=new svm_node();
		pa2.index=2;
		pa2.value=3.0;
		svm_node pb1=new svm_node();
		pb1.index=1;
		pb1.value=1.0;
		svm_node pb2=new svm_node();
		pb2.index=2;
		pb2.value=2.0;
		svm_node[] pa={pa1,pa2};
		svm_node[] pb={pb1,pb2};
		svm_node[][] p={pa,pb};
		double[] lables = {1.0, -1.0}; //a,b 对应的lable
		
		/*
		 * 定义svm_problem对象
		 */
		svm_problem problem=new svm_problem();
		problem.l = 2; //向量个数
		problem.x=p;
		problem.y=lables;
		
		/*
		 * 定义svm_parameter
		 */
		svm_parameter parameter=new svm_parameter();
		parameter.svm_type=svm_parameter.C_SVC;
		parameter.kernel_type=svm_parameter.LINEAR;
		parameter.cache_size=100;
		parameter.eps = 0.00001;
		parameter.C = 1;
        
		/*
		 * 训练svm分类模型
		 */
		System.out.println(svm.svm_check_parameter(problem, parameter));
		svm_model model=svm.svm_train(problem, parameter);
		
		/*
		 * 定义测试数据点c={2.0 3.0}
		 */
		svm_node pc1=new svm_node();
		pc1.index=1;
		pc1.value=2.0;
		svm_node pc2=new svm_node();
		pc2.index=2;
		pc2.value=3.0;
		svm_node[] pc={pc1,pc2};
		
		/*
		 * 预测测试数据的lable
		 */
		double res=svm.svm_predict(model, pc);
		System.out.println("预测结果为："+res);
		
	}
}
