My assignment work base on 4 parts. The first part is the imageio interface and belongs interface and classes. This part has two branches. Those are read and write image and multilayer image information text. Imageio is controlled by ioControl, It can load an image by reading record.txt and chose io class for different image types. 
The second part is the controller, This part reads the command and operates the multilayer model. The controller does not read and write images but it can control the image io through ioControl. 
The third part is the Multilayer model, this part is a multilayer image manage model. 
The last part is the view. The view can be text interactive view or graphics view.

design change:
1. I add 2 new methods in the controller to make the view can get information from the controller.
2. Take view out controller class to make the design fit MVC model
3. Add mosaic and downscale function to single image modify model and multi-image modify the model.So we can use those functions in the controller.
4. modify the controller constructor to add a view into the controller. That makes the controller can control the view.
5. For make mosaic function, I add a method in Filer class. for make downscale， I make a new interface ImageSizeChange. All image upscale and downscale function should under this interface.

Picture1：big_buck_bunny_blender3d from a open source cg moive Big Buck Bunny.
Picture2: b.ppm generate by my assignment 5 checker board generate function.
