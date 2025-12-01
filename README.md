# Assignment :  Restaurant Management Application
Developer : Karzaf HO / Ho Lok Yin</br>
Email :　lok.y.ho@students.plymouth.ac.uk / 20341080@learner.hkuspace.hku.hk

---

# 🍣 SushiHub - Restaurant Management App

A modern Android restaurant management application with separate interfaces for staff and customers.

---

## 🔐 **LOGIN CREDENTIALS**

### **Admin Access:**
```
Username: admin
Password: admin123
```
**Can access:** Menu management, View all orders, Admin dashboard

### **Guest Access:**
- Create new account on login screen
- Or browse with limited functionality

---

## 📱 **APP PAGES**

### **1. Start Page**
- Welcome screen
- "Get Started" button → Login

### **2. Login Page**
- Enter username and password
- Admin or Guest access
- Register new account option

### **3. Admin Dashboard** (Admin Only - `admin`/`admin123`)

**Three Tabs:**

**📋 Orders Tab**
- View all customer orders
- Order details (items, total, timestamp)
- Track order status

**🍣 Sushi Tab**
- View all sushi items
- ➕ Add new sushi
- ✏️ Edit sushi (name, price, description)
- 🗑️ Delete sushi

**🍹 Drinks Tab**
- View all drink items
- ➕ Add new drink
- ✏️ Edit drink (name, price, description)
- 🗑️ Delete drink

### **4. Guest Menu Page**
- Browse sushi items
- Browse drink items
- Add items to cart (➕ button)
- View cart (cart icon)

### **5. Checkout Page**
- View cart items
- Adjust quantities (+/-)
- Remove items (🗑️)
- See subtotal, tax (10%), total
- Place order button

### **6. Success Page**
- Order confirmation
- Order number/UUID
- Return to menu

---

## 🗄️ **DATABASE**

**4 Tables:**

1. **OrderTable** - Customer orders with items (JSON), totals, timestamp
2. **Account** - User accounts (default admin: admin/admin123)
3. **Sushi** - Sushi menu items (name, description, price, image)
4. **Drink** - Drink menu items (name, description, price, image)

**Sample Menu:**
- Sushi: Salmon Nigiri (¥850), Tuna Roll (¥950), California Roll (¥650)
- Drinks: Green Tea (¥300), Sake (¥800), Asahi Beer (¥600)

---

## 🚀 **HOW TO RUN**

1. **Open in Android Studio**
2. **Sync Gradle**
3. **Run** (▶️ button)
4. **Login:**
   - Admin: `admin` / `admin123`
   - Guest: Create account or browse

---

## 🎯 **QUICK TEST**

### **Test Admin Functions:**
1. Login: `admin` / `admin123`
2. Go to Sushi tab
3. Click ➕ to add new sushi item
4. Edit an existing item (✏️)
5. Delete an item (🗑️) - confirm deletion
6. Check Orders tab
7. Logout

### **Test Guest Functions:**
1. Login as guest (create account)
2. Browse sushi menu
3. Add items to cart (➕)
4. Browse drinks menu
5. Add drinks to cart
6. Click cart icon (top right)
7. Adjust quantities
8. Click "Place Order"
9. See confirmation

---

## 📂 **PROJECT STRUCTURE**

```
app/src/main/
├── java/com/karzaf/sushihub/
│   ├── Admin/          # Admin dashboard & adapters
│   ├── Database/       # Database helper & models
│   ├── Guest/          # Guest pages
│   └── Cart/           # Shopping cart logic
│
└── res/
    └── layout/         # All XML layouts
        ├── start.xml
        ├── login.xml
        ├── admin.xml
        ├── menu.xml
        ├── checkout.xml
        └── successful.xml
```


## 🎨 **KEY FEATURES**

✅ **For Admin:**
- Complete menu management (CRUD)
- Order tracking
- Tab navigation (Orders/Sushi/Drinks)

✅ **For Guests:**
- Menu browsing with images
- Shopping cart
- Order placement
- Order confirmation

✅ **Technical:**
- SQLite database
- Material Design UI
- RecyclerView lists
- Singleton pattern (Database, Cart)

---

## ⚙️ **TECHNOLOGIES**

- **Platform:** Android (API 24-34)
- **Language:** Java
- **Database:** SQLite
- **UI:** Material Design
- **Layouts:** ConstraintLayout, RecyclerView

---

## 📝 **DEFAULT DATA**

**Admin Account:**
- Username: `admin`
- Password: `admin123`

**Sample Sushi Items:**
- Salmon Nigiri - ¥850
- Tuna Roll - ¥950
- California Roll - ¥650
- Dragon Roll - ¥1,200

**Sample Drinks:**
- Green Tea - ¥300
- Sake - ¥800
- Asahi Beer - ¥600
- Ramune - ¥400

---

## 🎓 **PROJECT INFO**

- **Student:** Ho Lok Yin
- **Module:** COMP2000HK Software Engineering 2
- **Institution:** University of Plymouth / HKU SPACE
- **Year:** 2024-2025
