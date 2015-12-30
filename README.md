# Mahout_Recommendation
用Mahout尝试分析阿里天池竞赛移动推荐算法题目
##相关介绍
Apache Mahout 是 Apache Software Foundation (ASF) 开发的一个全新的开源项目，其主要目标是创建一些可伸缩的机器学习算法。Mahout使用了Taste来提高协同过滤算法的实现，它是一个基于Java实现的可扩展的高效的推荐引擎。

## 相关接口
* DataModel 是用户喜好信息的抽象接口，它的具体实现支持从任意类型的数据源抽取用户喜好信息。
* UserSimilarity 用于定义两个用户间的相似度，它是基于协同过滤的推荐引擎的核心部分，可以用来计算用户的“邻居”（喜好相同）。
* UserNeighborhood 用于基于用户相似度的推荐方法中，推荐的内容是基于找到与当前用户喜好相似的邻居用户的方式产生的。
* Recommender 是推荐引擎的抽象接口，它可以计算出对不同用户的推荐内容。实际应用中，分别实现基于用户相似度的推荐引擎或者基于内容的推荐引擎。
* RecommenderEvaluator ：评分器。
* RecommenderIRStatsEvaluator ：搜集推荐性能相关的指标，包括准确率、召回率等等。
