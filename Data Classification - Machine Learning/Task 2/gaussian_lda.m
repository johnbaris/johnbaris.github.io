function [class, conf, accuracy, determinant] = gaussian_lda( test_c,train_c,train,test,b )

format longG
Prob = zeros(10,size(test,1));   % Matrix where we will store the probabilities for each class
class = zeros(1,size(test,1));   % Matrix where we will store the feature vectors of each class
avg = zeros(10,size(train,2));   % Matrix where we will store the mean values for each class
Sh_Cov = zeros(size(train,2),size(train,2));   % Shared Covariance matrix

for i=1:10    
    avg(i,:) = MyMean(train(find(train_c==i),:));
end


for i=1:10
         
    cm = train(find(train_c==i),:);
    S = bsxfun(@minus, cm, avg(i,:));
    Cov = (S.'*S)/1000;
    Sh_Cov = Sh_Cov + Cov;
end
    Sh_Cov = Sh_Cov/10;
    determinant = det(Sh_Cov);
for i=1:10
    Prob(i,:) = gaussianDisc(avg(i,:),Sh_Cov,test);
end

max = - 100000000;
for i=1:size(Prob,2)
    for j=1:10
       if(Prob(j,i)>max)
          max = Prob(j,i);
          class(1,i) = j;
       end
    end
    max=-100000000;
end

[conf, accuracy] = MyConfusion(test_c,class);


end
