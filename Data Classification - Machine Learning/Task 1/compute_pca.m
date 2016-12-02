function [EVecs, EVals] = compute_pca(X)

[rw, cl] = size(X);
M = zeros(1,cl);
M = MyMean(X);

Cov = MyCov(X,M);

EVals = eig(Cov);
[R,D] = eig(Cov);
EVecs = R;

%We sort the eigenvalues in a decreasing order and the correspoding
%eigenvectors at the same time
[EVals,indices] = sort(EVals, 'descend');
EVecs = EVecs(:,indices);

%We make sure that the first element of each eigenvector is non-negative
for i=1:size(EVecs,2)
    if(EVecs(1,i)<0)
       EVecs(:,i) = EVecs(:,i)*(-1);
    end
end

end

