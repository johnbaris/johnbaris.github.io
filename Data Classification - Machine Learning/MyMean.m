function [ Mean ] = MyMean( A )

Mean = zeros(1,size(1,2));

for i=1:size(A,2)
    Mean(1,i) = sum(A(:,i))/size(A,1);

end

