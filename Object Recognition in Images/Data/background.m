function b = background(images)
   
    %Function we created generate background from a set of images
    array = double(images);   
    s = size(images,4);
    
    %Normalise the images
    for i = 1:s
        array(:,:,:,i) = normalise(images(:,:,:,i));
    end
    
    %Median filtering of the normalised images
    b = median(array,4);
end

   