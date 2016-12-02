function [M,I,A,back] = train()
    
%load all the images
    img1 = imread('02.jpg');
    img2 = imread('03.jpg');
    img3 = imread('04.jpg');
    img4 = imread('05.jpg');
    img5 = imread('06.jpg');
    img6 = imread('07.jpg');
    img7 = imread('08.jpg');
    img8 = imread('09.jpg');
    img9 = imread('10.jpg');
    img10 = imread('17.jpg');
    img11 = imread('18.jpg');
    img12 = imread('19.jpg');
    img13 = imread('20.jpg');
    img14 = imread('21.jpg');
    
    %Load the images we use for the background subtraction
    %Removed two of the images in order to get a better background
    
    %images = cat(4,img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14);
    %images = cat(4,img3,img4,img5,img6,img7,img8,img9,img11,img12,img13,img14);
    images = cat(4,img2,img3,img5,img6,img7,img8,img9,img11,img12,img13,img14);
    
    %The images we used for training
    array= {img1,img2,img3,img4,img5,img6,img7,img8};
    
    %Generate the background
    back = background(images);
    
    %Display steps of image processing 
    %figure()
    %imagesc(back)
    %disp(size(back))
    %figure()
    %n = normalise(img8);
    %disp(size(n))
    %imagesc(n);
    %figure()
    %imagesc(abs(n-back));
    
    %Generate a black feature vector for the training
    features = zeros(1,4);
    count = 1;    
    [asdf, n] = size(array);
    
    %figure()
    %imagesc(array{1})
    
    %Start training process for the images
    for i = 1:n     
        %Our thresholding function that uses the image and the background
        %to return the detected objects
        cells = thresholding(array{i},back);
    
        %figure()
        %imagesc(array{i});
    
        [nr,asdf] = size(cells);
    
        %Create feature vectors for each object
        for j = 1:nr
           %Our properties function takes black and white object and
           %original image to return the four features of our objects
           p = myproperties(cells{j},array{i});
           
           features(count,:) = p;
           count = count + 1;
                    
           %figure()
           %imagesc(cells{j});   
        end     
    end
    
    [N,DIM] = size(features);
    
    %True labels of the objects for training
    labels = [8,6,7,9,9,10,10,7,4,7,1,5,8,9,6,7,4,6,10,6,9,8,10,2,7,5,1,3,8,4,6,7,4,10,4,8,10,2,3,1,2,6,2,8,4,5,8,9,7,9,1,6,9,6,7,10,9,8,4,4,8,1,6,9,8,6,7,10,4,5,3,7,4,7,1,5,6,9,8,2,10,2,5,1,4,5,7,3,7,7,4,6,8,2,10,7,4,3,1,7,7,5,6,4];
        %6,2,7,10,8,9,7,1,7,3,8,2,6,2,5];
    
    %Build model for testing
    [M,I,A] = buildmodel(DIM,features,N,10,labels);    
end
