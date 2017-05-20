---
title: 并查集解决连通性的有效算法
date: 2017-05-19 21:06:05
tags: Java Algorithms
category: Algorithms
---
> 连通性 
**假设 p 与 q 连接， q 与 r 连接，那么 q 与 r 也是连通的，这就是连通性， 且连通性具有传递性。**


写这篇文章解决的问题是什么呢？不能瞎忙乎。所以，先来出一个题目供大家思考

> 我们不断的输入如一下成对出现的数字，利用所看到充分信息来判断两个数字是否具有连通性。
p - q
3 - 4
4 - 9
8 - 0
2 - 3
5 - 6
2 - 9
5 - 9
7 - 3
4 - 8
5 - 6
0 - 2
6 - 1

这个问题存在许多中套的应用中，如以下的两个例子。

* 如整数代表是大型网络中的计算机，对(就是这个符号 ``-`` )可能代表网络中的连通。所以需要判断是否建立一个直连进行通信还是利用已有的连通建立一条通信路径。

* 整数代表电网中的一个触点，而这些对表示连通点的电线。我们可以使用程序来查找连通所有点而没有任何多余连通的路径(即禁止环状结构出现)

##基本运算
根据以上问题的描述，我们设计出有以下的两个功能的算法和数据结构来解决问题

* ``数组`` 作为我们最基本的数据结构
* ``find`` 查找对象是否在同一个集合
* ``union`` 用来连通两个对象

通过查找 (``find``) 和并集 (``union``) 的抽象运算，很容易解决连通性问题。

**从输入读取一个新的 ``p - q`` 对后，执行对 p 和 q 的查找运算来判断是否在同一个集合中。如果在同一个集合中那么不作改变，如果不在同一个集合中则进行并集运算，即连通两个对象。**

##快速查找(Quick-Find)

###算法思想

首先是我们基础的数据结构，数组，我们所有的一切都是在数组的基础上完成的
|i|0|1|2|3|4|5|6|7|8|9|
|::|:--:|:--:|:--:|:--:|:--:|:--:|
|res[i]|0|1|2|3|4|5|6|7|8|9|
以上表格代表的意思是刚开始的每个点只和自己连接，彼此独立存在


每输入一个 ``p - q`` 对的时，我们设立一个规则，**将与前者的集合合并到后者的集合**
如 3 - 4

|i|0|1|2|3|4|5|6|7|8|9|
|::|:--:|:--:|:--:|:--:|:--:|:--:|
|arr[i]|0|1|2|**4**|4|5|6|7|8|9|
我们将 res[3] 的值改为 res[4] 的值一样从而表示 3 与 4 连接

**但是，假设这个时候有跟 3 同一集合的也应该并入与 4 的同一集合**，所以我们还得遍历与 3 在同一集合的还有哪些
那么整个算法呼之欲出了

###Java 实现
``` java
public static int[] excute3(int[][] data) {
    res = Utils.init(res);
    for (int i = 0; i < data.length; i++) {
        int p = data[i][0];
        int q = data[i][1];
        //优化  如果相等则不需要遍历
        if (res[p] == res[q]) {
            continue;
        }
        int j, temp;
        for (j = 0, temp = res[p]; j < res.length; j++) {
            if(res[j] == temp){
                res[j] = res[q];
            }
        }
    }
    return res;
}
```
测试的数据是本文刚开始数据

测试的过程是

|p - q|0|1|2|3|4|5|6|7|8|9|
|:-:|:--:|:--:|:--:|:--:|:--:|:--:|:-:|:-:|:-:|:-:|
|3 - 4|0|1|2|**4**|4|5|6|7|8|9|
|4 - 9|0|1|2|**9**|**9**|5|6|7|8|9|
|8 - 0|0|1|2|9|9|5|6|7|**0**|9|
|2 - 3|0|1|**9**|9|9|5|6|7|0|9|
|5 - 6|0|1|9|9|9|**6**|6|7|0|9|
|2 - 9|0|1|9|9|9|6|6|7|0|9|
|5 - 9|0|1|9|9|9|**9**|**9**|7|0|9|
|7 - 3|0|1|9|9|9|9|9|**9**|0|9|
|4 - 8|0|1|**0**|**0**|**0**|**0**|**0**|**0**|0|**0**|
|5 - 6|0|1|0|0|0|0|0|0|0|0|
|0 - 2|0|1|0|0|0|0|0|0|0|0|
|6 - 1|**1**|1|**1**|**1**|**1**|**1**|**1**|**1**|**1**|**1**|


可见最后都是在一个集合的

但我们也看到了这个算法还很粗糙
**当有 N 个对象，M 个并集运算的连通性问题，至少执行 MN 条命令**

所以我们换一个思路

##快速并集(Quick-Union)

###算法思想
不知道诸位是否知道树的构成，其实连通就是将各个子树合并成一颗大树
如 p - q 即 res[p] = q 
也就是从 p 指向了 q，但我们这里不仅仅是指向了 q 而是指向了 q 的根
所以我们明白如果在数组中判断哪一个点是根节点，那么它一定是 res[p] = p;
我们将前者的根连接到后者的根上那岂不是将两棵树合并了？(PS:我刚刚想到了更好的主意，将前者的每一个点都连接到后者的根形成一颗矮树，这就是快速压缩路径并集)

###Java 实现
``` Java
public class QuickUnion {

    /**
     * 
     * @param data 模拟输入的数据
     * @param length 连通的对象数
     * @return
     */
    public static int[] excute1(int[][] data,int length) {
        int[] res = new int[length];
        res = Utils.init(res);

        for (int i = 0; i < data.length; i++) {
            int frontRoot = data[i][0];
            int tailRoot = data[i][1];

            //寻找做合并的根
            frontRoot = find(frontRoot, res);
            tailRoot = find(tailRoot, res);

            union(frontRoot, tailRoot, res);
            Utils.printAll(res);
        }

        return res;
    }

    public static int find(int root, int[] data) {
        if (data[root] != root) {
            find(data[root], data);
        }
        return data[root];
    }

    public static void union(int front, int tail, int[] data) {
        data[front] = data[tail];
    }
}
```

我们可以通过下表动态观察数组中每个值的变化

|p - q|0|1|2|3|4|5|6|7|8|9|
|:-:|:--:|:--:|:--:|:--:|:--:|:--:|:-:|:-:|:-:|:-:|
|3 - 4|0|1|2|**4**|4|5|6|7|8|9|
|4 - 9|0|1|2|4|**9**|5|6|7|8|9|
|8 - 0|0|1|2|4|9|5|6|7|**0**|9|
|2 - 3|0|1|9|4|9|5|**9**|7|0|9|
|5 - 6|0|1|9|4|9|**6**|6|7|0|9|
|2 - 9|0|1|9|4|9|6|6|7|0|9|
|5 - 9|0|1|9|4|9|6|**9**|7|0|9|
|7 - 3|0|1|9|4|9|6|9|**9**|0|9|
|4 - 8|0|1|9|4|9|6|9|9|0|**0**|
|5 - 6|0|1|9|4|9|6|9|9|0|0|
|0 - 2|0|1|9|4|9|6|9|9|0|0|
|6 - 1|**0**|1|9|4|9|6|9|9|0|0|

所以我们在查找两个对象是否在同一个集合的时候可以直接使用 find 方法来判断根是否是同一个，从而判断是否在同一个集合 。

而合并的时候可以将要合并的树的根节点指向后者就可以轻松实现了。
但是我们的这个算法还有优化的余地，使用加权快速并集来实现。

###加权快速并集(Weighted Quick-Union) 
不知道各位有没有想过这样一个问题，那就是在合并树的时候是大树往小树合并还是小树往大树合并？
我们可以想象一下，若一直坚持大树往小树合并，则树会越来越高，不利于 ``find``, 所以正确的方案是小树向大树合并

* 当经常进行对象是否在同一个集合的查询时，将会查找这个对象的根节点，那么查找的这个节点离根越近则意味着查询越快，这也意味着我们的树是一颗矮树，那么合并的时候要下大功夫
* 当经常进行合并这个操作的时候，我们只需将小树指向大树的根就可以

但我们遇到了一个难题
**那就是到底谁是大树，谁是小树？**

####算法思想
我们可以通过节点的数目**粗略**的判断两棵树的大小
所以可以采用一个 weight 数组来存储当前节点的数目
刚开始每个数组的几点都是 1

|i|0|1|2|3|4|5|6|7|8|9|
|::|:--:|:--:|:--:|:--:|:--:|:--:|
|weight[i]|1|1|1|1|1|1|1|1|1|1|

举个例子
当  3 - 4 时
先判断 3 和 4 分别找到其根节点，然后拿两个根的权重作比较
然后拿权重小的并向大的，因为这里两个集合的权重相等，所以我们就拿 3 并向 4
则 weight[4] = weight[3] + weight[4]
我们可以想象一下这颗树是什么样子
根是 4 ，权重是 2
叶还是 3 ，权重是 1，

####Java 实现
```Java
public class WeightedQuickUnion {

    private static int[] res = new int[10];
    private static int[] weight = new int[10];

    public static int[] excute1(int[][] arr) {
        res = Utils.init(res);

        //用来存储节点数，从而保证小树向大树合并
        for (int i = 0; i < weight.length; i++) {
            weight[i] = 1;
        }

        for (int i = 0; i < arr.length; i++) {
            int frontRoot = arr[i][0];
            int tailRoot = arr[i][1];

            frontRoot = find(frontRoot);
            tailRoot = find(tailRoot);

            //不是同一集合需要合并
            if (frontRoot != tailRoot) {
                //小树合并到大树上
                if(weight[frontRoot] < weight[tailRoot]){
                    union(frontRoot, tailRoot);
                } else {
                    union(tailRoot, frontRoot);
                }
            }
        }
        return res;
    }

    public static int find(int root) {
        int count = 1;//用来记录树节点中节点数目
        while (res[root] != root) {
            count++;
            root = res[root];
        }
        weight[root] = count;
        return res[root];
    }

    public static void union(int frontRoot, int tailRoot) {
        res[frontRoot] = tailRoot;
    }
}
```

但是但是，这个算法还可以优化，我觉得我通过写了前面的三个算法外加我在文中的一个(PS)
也许有人能猜出来是啥了，但是我不打算写了

提醒下大家
加权压缩路径快速并集算法，若有错误，还请指出(〃'▽'〃)

代码已上传到 [github](https://github.com/DraperHXY/Union-Find)