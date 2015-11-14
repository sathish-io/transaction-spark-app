# transaction-spark-app
Spark App that users  dummy transaction data set to analyze it    

## Usage

Build the app using below command
```
activator package
```
Submit the app to the spark standalone cluster using below command

./bin/spark-submit --class com.sathish.spark.app.TxnSparkApp  --conf spark.eventLog.enabled=true --conf spark.eventLog.dir=hdfs://<hadoop-master>:8020/user/ctier/spark-events --master spark://<spark-master>:7078  ../txn-spark-app_2.10-1.0.jar   hdfs://<hadoop-master>:8020/user/ctier/data/file1.txt

Replace below name according to your cluster setup
 
<spark-master>
<hadoop-master>

