% gets property vector for a binary shape in an image
function vec = myproperties(Image,Orig)

    area = bwarea(Image);
    perim = bwarea(bwperim(Image,4));
     
    % compactness
    compactness = perim*perim/(4*pi*area);

    % get scale-normalized complex central moments
    c11 = complexmoment(Image,1,1) / (area^2);
    c20 = complexmoment(Image,2,0) / (area^2);
    c30 = complexmoment(Image,3,0) / (area^2.5);
    c21 = complexmoment(Image,2,1) / (area^2.5);
    c12 = complexmoment(Image,1,2) / (area^2.5);

    % get invariants, scaled to [-1,1] range
    ci1 = real(c11);
    ci2 = real(1000*c21*c12);
    tmp = c20*c12*c12;
    ci3 = 10000*real(tmp);
    ci4 = 10000*imag(tmp);
    tmp = c30*c12*c12*c12;
    ci5 = 1000000*real(tmp);
    ci6 = 1000000*imag(tmp);

           
    [R,C] = size(Image);
    b = [1,255];
    
    %Create a feature representing the colour blue that we wanted to add to
    %training
    for x = 1:R
        for y = 1:C
            if (Image(x,y) ~= 0)
                bb = [double(Orig(x,y,1))];
                b = cat(2,b,bb);
            end
         end
    end

    blu = median(b);

           
    %vec = [compactness,ci1,ci2,ci3,ci4,ci5,ci6];        
    vec = [compactness,perim,ci1,blu] ;  

end