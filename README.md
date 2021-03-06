# IR-Assignment2

### Comarison of different algorithms

| Evaluation Metric | Your Algorithm | Vector Space | BM25 | LM with Dirichlet | LM JM Smoothing |
|-------------------|-----------------|---------------|---|--------------------|------------------|
|P@5 |0|0.6|0.6000|0.8000|0.6000|
|P@10|0|0.6|0.5000|0.6000|0.5000|
|P@20|0|0.4|0.3000|0.3500|0.3000|
|P@100|0|0.09|0.0210|0.1000|0.1000|
|Recall @ 5|0|0.09|0.0968|0.1290|0.0968|
|Recall @10|0|0.1935|0.1613|0.1935|0.1613|
|Recall @ 20 |0|0.2581|0.1935|0.2258|0.1935|
|Recall @ 100 |0|0.29|0.3226|0.3226|0.3226
|MAP|0|0.21|0.2032|0.2345|0.1613|
|MRR|0|1.0|1.0|1.0000|1.0000|
|NDCG@5|0|0.68|0.7227|0.8688|0.6844|
|NDCG@10|0|0.14|0.6208|0.7084|0.5959|
|NDCG@20|0|0.1936|0.4361|0.4945|0.4209|
|NDCG@100|0|0.1936|0.4102|0.4352|0.3968|

| Evaluation Metric | Your Algorithm | Vector Space | BM25 | LM with Dirichlet | LM JM Smoothing |
|-------------------|-----------------|---------------|---|--------------------|------------------|
|P@5 |0|0.8000|0.6000|0.4000|0.6000|
|P@10|0|0.5000|0.5000|0.4000|0.4000|
|P@20|0|0.3000|0.3000|0.3500|0.3000|
|P@100|0|0.1200|0.1100|0.1000|0.1000|
|Recall @ 5|0|0.1290|0.0968|0.0645|0.0968|
|Recall @10|0|0.1613|0.1613|0.0968|0.1290|
|Recall @ 20 |0|0.1935|0.1935|0.2258|0.1935|
|Recall @ 100 |0|0.3871|0.3548|0.3226|0.3226|
|MAP|0|0.2170|0.0968|0.1043|0.1707|
|MRR|0|1.0000|1.0000|0.3333|1.0000|
|NDCG@5|0|0.8688|0.7227|0.3008|0.7227|
|NDCG@10|0|0.6422|0.6086|0.2686|0.5326|
|NDCG@20|0|0.4492|0.4301|0.3156|0.4176|
|NDCG@100|0|0.4536|0.4205|0.2899|0.3912|

### Summary of findings:
For short queries, LM with dirichilet smoothing seems to give a better performance. When compared with other algorithms, it has atleast 0.2 more from P@5. It has better recall, MAP and NDCG.
However for longer queries, results were similar. Certain algorithms performed better at certain instances. Eg, vector space perfomed best for P@5, Recall, MAP and NDCG. However for P@20, LM with dirichlet smoorthing performed the best. This may be attributed due to the fact that since vector model has more words, it might be working better.
