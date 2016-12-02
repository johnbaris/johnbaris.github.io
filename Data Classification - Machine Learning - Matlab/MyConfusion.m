function [ confusion, class_rate ] = MyConfusion(test_c, classes)

confusion = zeros(10,10);

for i=1:size(test_c,2)
    confusion(test_c(1,i),classes(1,i)) = confusion(test_c(1,i),classes(1,i)) + 1;
end
class_rate = 0;
for i=1:10
    class_rate = class_rate + confusion(i,i);
end
class_rate = class_rate/1000;



end

