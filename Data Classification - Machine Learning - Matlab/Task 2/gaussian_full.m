function [class,confusion, class_rate, determinant] = gaussian_full( test_c,train_c,train,test,a )

format longG
Prob = zeros(10,size(test,1));   % Matrix where we will store the probabilities for each class
class = zeros(1,size(test,1));   % Matrix where we will store the feature vectors of each class
avg = zeros(10,size(train,2));   % Matrix where we will store the mean values for each class
determinant = zeros(1,10);


for i=1:10    
    avg(i,:) = MyMean(train(find(train_c==i),:));
end

% Algorithm for calculating the desired probabilities
for i=1:10
         
    cm = train(find(train_c==i),:);
    S = bsxfun(@minus, cm, avg(i,:));
    Cov = (S.'*S)/1000;
    determinant(1,i) = det(Cov);
    Prob(i,:) = gaussianMV(avg(i,:),Cov,test);
end

% Find the biggest probability for each point and assign the corresponding
% class
max = - 10000000000;
for i=1:size(Prob,2)
    for j=1:10
       if(Prob(j,i)>max)
          max = Prob(j,i);
          class(1,i) = j;
       end
    end
    max=-10000000000;
end

[confusion, class_rate] = MyConfusion(test_c,class);


end

