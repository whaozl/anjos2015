使用方法：

管理员：
1.先调用AdminUi->TrainProcess处理成libsvm语料格式
2.再调用AdminUi->UiMain生成模型

用户：
1.先调用Action->WordCut 对默认的目录进行分词处理
2.再调用Action->Classify 进行分类


附加说明：

1.训练阶段是在【管理员】部分

2.分类部分是在【用户】部分

3.因此运行的步骤是：
	(1)运行AdminUi->TrainProcess
	(2)运行AdminUi->UiMain
	(3)运行Action->WordCut
	(4)运行Action->Classify