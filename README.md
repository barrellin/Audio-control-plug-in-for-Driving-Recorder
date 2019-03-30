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

百度语音识别 http://ai.baidu.com/tech/speech/asr

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

然后就根据官方教程下载预构建的 Android 演示应用观看效果，目前发生解析包时出现问题的错误，应该需要安装更高级系统进行尝试。

之后在根据教程运行 wav_to_spectrogram 工具，查看音频样本生成的图像类型时，发生如下报错，目前仍在进一步研究教程解决中：
![2](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/3.png)

### 2018.1.16~2018.2.22

考虑到了需要对屏幕进行模拟点击，我进行了相关的资料调查，项目的实现目标是需要根据语音进行反馈的模拟点击，那么就是要实现一个“声控”的“按键精灵”。当前需要实现模拟点击的话，主要可以通过三种方法：①AccessibilityService辅助类；②Instrumentation方法；③adb shell命令的方法。其中，AccessibilityService辅助类是安卓系统设计出来帮助残障人士使用手机的，优点是不root可用，缺点是要找到页面空间进行模拟点击；Instrumentation方法虽然可以点击屏幕上的任何位置，在如今第三方Rom横行的今天，基本上是不可能获取到INJECT_EVENTS这一个权限的；adb shell命令的话，需要root过的手机。为了app的通用性考虑，我选择使用AccessibilityService辅助类来实现模拟点击功能。

考虑到之后可能要测试多款行车记录仪软件，我对accessibility.xml进行相关配置的时候，没有限定针对某一个特定的app，如图。

![4](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/4.png)

![5](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/5.png)

然后就是选定测试软件了，我从华为应用市场上随机挑选了一个行车记录仪app来进行测试，如图。

![6](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/6.png)

选定一款测试软件之后，使用Androidstudio自带的功能获取它的包名和控件名，如图。


![7](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/7.png)

在tensorflowdemo的speechactivity中，通过观察录音的线程和识别的线程的代码，如图所示：

![9](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/9.png)

![10](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/10.png)

可以知道，录音时，数据流是连续不断的，而每次拷贝的录音数据和上一次是可能存在重复的，这保证了录音数据的延续性，这样的处理基本能保证捕捉到关键数据。那么只要在service中相应的启动录音的线程和识别的线程，并且加以识别，是可以读取出对应识别的结果的标签的，如图所示：

![12](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/12.png)

因为tensorflow训练好的模型给出的label比较有限，不妨先简单的把start和stop的标签用于进行对录制视屏按钮的虚拟点击，把yes的标签用于对拍摄按钮的虚拟点击，这样就可以实现了通过语音的service实现对一个app的按钮的语音控制的功能。

tensorflow给的例子speechactivity是在一个activity中进行录音以及相关识别，而为了实现模拟点击，我需要把这一段录音以及识别转移到service中，目前service已经能开始进行录音与识别，但在执行过程中会发生报错，正在调试。

![8](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/8.png)

### 2018.2.23~2018.3.29
改用百度语音识别服务，重编程序逻辑，如下所示：

①MainActivity的逻辑实现
MainActivity的逻辑流程图如下图所示：

![31](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/31.jpg)

由于本文设计是需要辅助功能的，因此，若是需要用户自行打开设置-辅助功能菜单去设置辅助功能的话，交互性会变差，与做一个方便的插件的初衷背道而驰，因此，在点击事件发生的时候，不是直接开启服务，而是先判断辅助功能是否已经打开，若未打开，则应该自动跳转至手机的系统设置-辅助功能目录下，等待用户授权。用户授权之后，则可以开始相对应的辅助功能服务，发送相应的startService事件。



②MySpecialService的逻辑实现
MySpecialService的逻辑流程图如下图所示：

![32](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/32.jpg)

MySpecialService，对应的是“预设语音识别服务”，为什么说是“预设”的呢，这是因为该服务采用的是传统的Accessibility Service使用方法，即在代码中直接使用对应的view的id来进行节点查找以及后续的模拟点击。这种方法，语音识别后的模拟点击的控件不是用户可以修改的，该“预设语音识别服务”功能对应的app，是奇点行车记录仪，为在网上随机挑选的一款简易行车记录仪app。
在配置该Accessibility Service的时候，监听的系统事件定义为窗口界面变化事件、点击事件、以及窗口像素变化事件。其中，监听窗口界面变化事件是用于当程序切换到行车记录仪的主界面时，获取当前界面信息并进行对比，若是奇点行车记录仪的主界面，则记录节点信息为当前系统事件的触发源，即节点信息在记录为行车记录仪的主界面，那么，在后续进行通过id寻找控件时，能保证两个控件均为记录下来的节点信息的孩子，即确保能寻找到控件。
每次系统监听到设定的系统事件的时候，变回开启并且重置系统服务，初始化各项参数，点击事件的监听可以确保每次语音识别以及模拟点击之后，让整个后台服务回到就绪的状态，准备好下一次的监听。而考虑到，百度语音识别服务调起之后，若是长时间无声音或者识别出来的词语无对应操作，则语音识别服务就会停止，并且语音识别服务本身是无法调起下一次的语音识别服务的。为了本文设计能在后台保持语音监听状态，采用了监听窗口像素变化事件。因为前台软件是行车记录仪app，是在调用摄像头的，因此，窗口内的像素是一定发生变化的，通过监听窗口像素变化事件，则可以保持百度语音识别服务一直处于就绪状态，同时，不断的触发服务，释放资源，达到了一个良好的持续监听的效果。
在Accessibility Service配置完毕之后，就要开始编写百度语音识别服务的回调函数了。其默认的回调函数，是在logcat处输出语音识别的结果。因此，在override百度语音的回调函数onEvent的时候，我们可以直接获取到识别结果的json串，通过对json串进行处理，提取出识别结果，然后根据识别结果进行对应的操作就可以了。
提取出识别结果，若为“拍照”，则用上文中提到的设置好的节点信息，通过id photo_capture获取到拍照按钮的控件节点信息，然后使用Accessibility Service的ACTION_CLICK，就可以模拟点击到拍照按钮了，点击完后，释放资源，防止下一次识别出错。
若为“开始录像”或者“停止录像”则使用相同的方法，通过id video_recoder获取到录像按钮的节点信息，再根据一个boolean flag来确定是“开始录像”还是“停止录像”生效就可以了。同样的，模拟点击后释放资源。
至此，“预设语音识别服务”的基础功能就设计好了。

③MyService的逻辑实现
MyService的辅助功能部分的逻辑流程图如下图所示：

![331](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/331.jpg)

MyService对应的是“通用语音识别服务”，顾名思义，与“预设语音识别服务”不同的是，该服务是不需要事前知道控件id的，是针对“预设语音识别服务”的功能强化版，除了奇点行车记录仪app以外，还可以对应其他的不同的简易行车记录仪app使用。
因为需要适用于多种不同的行车记录仪app，就不能像“预设”服务那样，通过监听界面窗口变化来对根节点信息直接赋值了，因为不清楚目标行车记录仪界面activity的id。因此，这里使用监听点击事件来解决虚招了两个boolean flag，拍照按钮设置对应的是PhotoFlag ，录像按钮设置对应的是VideoFlag，这两个flag只有在语音识别到“设置拍照”和“设置录像”的时候才会分别变为真值。然后在监听到了系统点击事件，并且对应的flag为真值时，意味着用户在设置拍照和录像的对应的按钮，因此，只需要把这个时候传入的事件的触发源（即被点击的按钮本身）记录为对应的信息节点就可以了。这样就实现了用户自定义“拍照”和“录像”按钮的效果。同时，在设置完后，要立刻把对应的flag设置为false，放置设置好的按钮因为监听到按钮点击事件而被再设置。
除开设置按钮的功能以外，保持百度语音识别服务就绪，随时可以进行语音识别的方法与“预设语音识别服务”是一样的，都是通过了监听窗口像素变化事件实现了，就不多作叙述了。

![332](https://github.com/barrellin/Audio-control-plug-in-for-Driving-Recorder/blob/master/%E5%9B%BE%E7%89%87%E7%B4%A0%E6%9D%90/332.jpg)

MyService的语音识别部分的逻辑流程图如上图所示：因为本文设计在使用“通用语音识别服务”功能的时候，一开始，拍照按钮的节点信息和录像按钮的节点信息都为空，因此需要特别小心，如果调出了一个空的节点信息来进行模拟点击的话，程序是会直接崩溃的，可是又需要“设置拍照”和“设置录像”的语音识别功能，因此一开始如果识别到“拍照”、“开始录像”和“停止录像”的时候，需要直接返回。然后，如上文所述，在识别到“设置拍照”和“设置录像”的时候，就需要把对应的boolean flag设置为true，然后由点击对应的按钮来记录对应的节点信息，这之后就可以正常使用“拍照”和“录像”功能了。
值得一提的是因为“预设语音识别服务”记录的节点信息为按钮所在的activity的信息，而这里直接记录的是对应按钮的节点信息，因此，调用ACTION_CLICK的时候就不需要通过view的id来找按钮的节点信息了。这也是本功能可以适用于多款行车记录仪的原因。

同时，在写好程序的同时，完成了论文的初稿。
