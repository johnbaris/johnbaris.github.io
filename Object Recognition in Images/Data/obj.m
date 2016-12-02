function val = obj(nr)
    
    %Takes the class of the objects to print its name and return its
    %value
    
    val = 0;
    if (nr == 1)
        disp('1 Pound Coin')
        val = 100;
    elseif (nr == 2)
        disp('2 Pound Coin')
        val = 200;
    elseif (nr == 3)
        disp('50p Coin')
        val = 50;
    elseif (nr == 4)
        disp('20p Coin')
        val = 20;
    elseif (nr == 5)
        disp('5p Coin')
        val = 5;
    elseif (nr == 6)
        disp('75p Small H Washer')
        val = 75;
    elseif (nr == 7)
        disp('25p Large H Washer')
        val = 25;
    elseif (nr == 8)
        disp('2p Angle Bracket')
        val = 2;
    elseif (nr == 9)
        disp('No Value Battery')
    elseif (nr == 10)
        disp('No Value Nut')
    end
end