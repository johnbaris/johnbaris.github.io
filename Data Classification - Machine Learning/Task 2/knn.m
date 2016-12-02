function [classes,confusion, class_rate] = knn(test_c,train_c,train,test,k)

sq_dist = zeros(size(test,1),size(train,1));

for i=1:(size(test,1))   
    sq_dist(i,:) = square_dist(train, test(i,:));
end


[sq_dist,indices] = sort(sq_dist',1,'ascend');
class = train_c(indices);

classes = mode(class(k,:),1);

[confusion, class_rate] = MyConfusion(test_c,classes);

end