function [ MyCov ] = MyCov(X,mean)
    S = bsxfun(@minus, X, mean);
    MyCov = (S.' *S)/(size(X,1)-1);
end

