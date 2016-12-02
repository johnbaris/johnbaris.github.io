Y = cumsum(EVals);
for i=1:size(EVals,1)
    X(i,1) = i;
end
plot(X,Y)