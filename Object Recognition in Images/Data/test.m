function test(M,I,A,back,string)

    %Read the test image and obtain the objects from it
    img = imread(string);
    cells = thresholding(img,back);
    
    %Show the image to compare to the location of objects
    figure()
    imagesc(img);
    
    [nr,asdf] = size(cells);
    total = 0;
    
    %Classify each object
    for j = 1:nr
        %Obtain the features of an object
        v = myproperties(cells{j},img);
        %Show which object is being classified
        figure()
        imagesc(cells{j});
        %Obtain the class and value of the object
        temp = classify(v,10,M,I,4,A);
        total = total + obj(temp);
    end
   
    disp(sprintf('The total value is %dp',total))

end