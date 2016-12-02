function cells = thresholding(img,back)
    
    %Function we created to extract individual objects from an image using
    %the background

    %Performed background subtraction
    aux = abs(normalise(img)-back);
    [R,C,d] = size(aux);
    
    %figure()
    %imagesc(img)
    %figure()
    %imagesc(aux)
    %figure()
    %imagesc(back)   
    
    %Split image into 3 colours
    red = aux(:,:,1);
    green = aux(:,:,2);
    blue = aux(:,:,3);

    %myhist(red,1)    
    %figure()
    %histogram(red)
    
    %Initialise the black and white image
    new = zeros(R,C);
    
    %Use thresholds we found by experimenting to identify objects from the
    %background
    for i = 1:R
        for j = 1:C
                if ((red(i,j)>0.053) | (green(i,j)>0.055) | (blue(i,j)>0.051))
                    new(i,j) = 1;
                end
        end
    end
    
    %We tried using the bwmorph function for processing 
    %new = bwmorph(new,'dilate',1);
    
    %figure()
    %imagesc(new)
    %figure()
    %imagesc(img)
    
    %Removed small areas that are noise
    blob = bwareaopen(new,225);
    %figure();
    %imagesc(blob);
    
    %Label each individual object
    [labels,nr] = bwlabel(blob,8);
    %figure();
    %imagesc(labels)
    
    cells = cell(nr,1);
    
    %Create the cell array of objects that the whole function returns
    for k = 1:nr
        temp = blob;
        for i = 1:R
            for j = 1:C
                if (labels(i,j) ~= k)
                    temp(i,j) = 0;

                end
            end
        end
        cells{k,1} = temp;
    end

    
end
    
        
        

    
    