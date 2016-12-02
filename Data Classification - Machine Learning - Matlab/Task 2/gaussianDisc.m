function y = gaussianDisc(mu, covar, X)
% Y = GAUSSIANMV(MU, COVAR, X) evaluates a multi-variate Gaussian
%density in D-dimensions at a set of points given by the rows of
%the matrix X. The Gaussian density has mean vector MU and
%covariance matrix COVAR.
%
% Copyright (c) Ian T Nabney (1996-2001)
[n, d] = size(X);
[j, k] = size(covar);
% Check that the covariance matrix is the correct dimension
if ((j ~= d) ||(k ~=d))
   error('Dimension of covariance matrix and data should match');
end
invcov = inv(covar);
mu = reshape(mu, 1, d); % Ensure that mu is a row vector
% Replicate mu and subtract from each data point

y = (mu*invcov)*X' - 0.5*mu*invcov*mu' + log(1/10);