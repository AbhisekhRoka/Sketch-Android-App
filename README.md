I wanted to make a Sketch app that isn't filled with “clutter,” instead something that is simple and elegant.
My app doesn’t have any extra features other than the most basic ones. 
It makes it a functional and simple app with just the essential tools.

---

I present the Sketch app! It uses a bitmap for the user to draw on as well as stores the bitmap in a hashmap so the user can click through their different sketches. 
It provides the user with with basic functionalities such as a color wheel, 3 different brush types, as well as different brush sizes for different situations!

---

<img width="335" height="745" alt="Screenshot 2025-11-22 at 6 14 50 PM" src="https://github.com/user-attachments/assets/68d1bd2b-3b8e-46d0-8d53-4cd4117a7b0a" />

<img width="335" height="745" alt="Screenshot 2025-11-22 at 6 59 50 PM" src="https://github.com/user-attachments/assets/dea7d221-cde4-49c7-9ae7-d3d2b79d9be1" />

---

<img width="335" height="745" alt="Screenshot 2025-11-22 at 6 54 38 PM" src="https://github.com/user-attachments/assets/ae8d895d-fbaa-41d1-9154-283d234bcae5" />
<img width="335" height="745" alt="Screenshot 2025-11-22 at 6 29 28 PM" src="https://github.com/user-attachments/assets/63dea2d8-78ad-45e9-a8b5-0276ea7191c1" />

---

“<” PreviousBtn: Gets the previous bitmap in the hashmap

“>” NextBtn: Gets the next bitmap in the hashmap

“Sketch, Page” : TextView title, Font: Cursive

“0”: TextView page number, Updates when next or previous buttons are clicked.

DoodleView: Space for sketching.
1. Added functions to change color
2. Added functions to save and update bitmap
3. Added hashmap to store bitmap
4. Added functions to change brush and brush size.

ColorWheelView: Colorwheel
colorSelectedListener:
1. Updates the color in doodleView.
2. Updates the background of the ColorsBtn.
(Made only visible when Colors is clicked)

ColorsBtn: 
1. Makes colorWheelView visible/invisible.

BrushBtn:
1. Updates brush type in doodleView.
2. Updates the Color to black since colorWheelView does not have black as a color.

SizeBtn:
1. Updates brush size in doodleView.
