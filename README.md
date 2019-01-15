# Audio-control-plug-in-for-Driving-Recorder

## 项目描述

### 制作的必要性
现在市面上的行车记录仪app，基本都是没有语音控制功能的，只拥有最简单的行车记录仪功能。而在开车的时候，这是十分不方便的，若是可以通过语音控制行车记录仪app拍照，则可以极大的提高软件使用的便利性，以及行车的安全性。

### 功能描述
该app可以对用户的声音做出响应，根据对应的声音在用户选定的对应地方触发点击事件，从而实现对一般的行车记录仪app的语音控制拍照，录像的功能。

### 不同之处
根据调查搜索，通过这种语音触发点击事件来实现对一个普通行车记录仪app的语音控制功能的类似插件的app目前是没有的，预期这个app对一般行车记录仪的实用性会有极大的提高。

## 项目的关键技术、参考文献。

安卓编程技术、简易的tensorflow应用

语音唤醒模型 https://github.com/tensorflow/models/tree/master/research/audioset

简单词汇识别 https://www.tensorflow.org/tutorials/sequences/audio_recognition

## 项目完成计划

因复习考研，考研于12月底结束，预计一月份开始进行毕业设计，利用假期时间完成，预计使用5-8周：

第一周：阅读相关文献，理解语音唤醒模型的原理和使用

第二周：参考阅读简单词汇识别的源码，装载的andriod环境中

第三、四周：针对项目功能进行编码

第五周往后：结合实际的行车记录仪app进行实验、改进项目

## 会议记录

### 文献阅读

"A Survey on Deep Transfer Learning"

作者的通过调查相关文件，整理出了关于深度迁移学习的调查论文，其具体贡献如下：
① 定义深度迁移学习，并创新性的将其分类为四类：基于实例的深度迁移学习、基于映射的深度迁移学习、基于网络的深度迁移学习、基于对抗的深度迁移学习；
② 回顾了目前关于四类深度迁移学习的研究工作，给出了每个类别的标准化描述和示意图。 

"Contextual Parameter Generation for Universal Neural Machine Translation"

这篇文章是在神经网络机器翻译(NMT)上面进行算法的改进，作者提出了对现有的NMT模型进行简单的修改，加入了语境参数生成器(CPG)。加入CPG之后，能够通过对语言进行特定参数化，使得单个训练出来的通用模型实现在多种语言之间进行转换，达到比普通模型更加优秀的效果。 

### 第一次会议
受到阅读自然语言方面的论文的影响，我想运用自然语言处理的相关技术，根据商品名称的自然语言特征来进行近似名称商品的推荐，从而做到绕开商品好评等影响因素，直接推荐名称相关的商品的目的。
老师点评：①实际意义不大，与商品推荐不同类型的商品刺激消费的目的背道而驰；②而且因为商品实际上存在的tag，不需要舍近求远自己处理商品名称。
因此，这个项目设想是没有实际意义的，被驳回。

### 第二次会议
针对老师在开会时提到过的选材范例和自己的兴趣，我阅读了Models for AudioSet: A Large Scale Dataset of Audio Events，提出了做一个给行车记录仪app加上语音控制功能的app的项目思路，得到了通过，老师根据项目，提出了具体实现的大概流程，并推荐了可用的相关代码和参考文献，让项目的整体更加的明确。

## 工作总结
### 2018/11/1~2018/11/15
因复习考研，只阅读了语音唤醒模型的少量相关资料
### 2018/11/16~2018/11/30
因复习考研，项目暂无进展
### 2018/12/1~2018/12/15
考研复习冲刺阶段，项目暂无进展
### 2018/12/16~2018/12/23
考研
### 2018/12/24~2018/12/31
①重新配置安装了Android studio，并根据手机系统版本进行相关配置；

②阅读研究 https://www.tensorflow.org/tutorials/sequences/audio_recognition 的简单词汇识别资料，并根据相关教程，配置安装tensorflow。

### 2018/1/1~2018/1/15
根据上述教程，使用tensorflow训练模型，如图所示，完成了全部 18000 个训练步，得到如下混淆矩阵：
![1](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/1.png)
然后就是创建冻结模型并且对模型进行测试，如图所示：
![2](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/2.png)
然后就根据官方教程下载预构建的 Android 演示应用观看效果。
之后在根据教程运行 wav_to_spectrogram 工具，查看音频样本生成的图像类型时，发生如下报错，目前仍在进一步研究教程解决中：
![2](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/3.png)

