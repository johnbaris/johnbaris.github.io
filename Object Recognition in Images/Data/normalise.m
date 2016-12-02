function norm = normalise(img)

    %Function we created to normalise an image
    
    %Separate the three colours 
    red = img(:,:,1);
    green = img(:,:,2);
    blue = img(:,:,3);
    
 %   disp(red(1,1))
 %   disp(green(1,1))
 %   disp(blue(1,1))
 
    %Normalise each colour
    nred = (double(red))./(double(red+green+blue));
    ngreen = (double(green))./(double(red+green+blue));
    nblue = (double(blue))./(double(red+green+blue));
    
    %Reform the image
    norm = cat(3,nred,ngreen,nblue);
    
end

    