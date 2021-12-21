package extension

import (
	"fmt"
	"github.com/golang-jwt/jwt"
	"golang.org/x/crypto/bcrypt"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"log"
)

var (
	DB, err = gorm.Open(mysql.Open("root:3777777@tcp(127.0.0.1:3306)/thyme?charset=utf8mb4&parseTime=True&loc=Local"), &gorm.Config{})
	key     = "Parsley Sage Rosemary and Thyme"
)



func init() {
	if err != nil {
		fmt.Println(err)
	}
	return
}

func GenerateToken() string {
	claims :=  jwt.StandardClaims{
		ExpiresAt: 15000,
		Issuer:    "Pigeon377",
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256,claims)

	headerMap := make(map[string]interface{})
	headerMap["alg"] = "HS256"
	headerMap["typ"] = "JWT"
	headerMap["iss"] = "Pigeon377"
	token.Header = headerMap

	tokenString, _ := token.SignedString(key)
	return tokenString
}

func ParseToken(tokenString string) (interface{}, bool) {
	token, err := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("unknown algorithm : %s", token.Header["alg"])
		}
		return []byte(key), nil
	})
	if err != nil {
		return nil, false
	}
	if claims, ok := token.Claims.(jwt.MapClaims); ok && token.Valid {
		return claims, true
	} else {
		fmt.Println(err)
		return nil, false
	}
}

func GeneratePasswordHash(password string) (string, error) {
	hashCode, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.MinCost)
	if err != nil {
		log.Println(err)
		return "error", err
	}
	return string(hashCode), nil
}

func CheckPasswordHash(needCheckPassword string, truePassword string) bool {
	err := bcrypt.CompareHashAndPassword([]byte(truePassword), []byte(needCheckPassword))
	if err != nil {
		return false
	}
	return true
}

//
//func GeneratePasswordHash(password string) string {
//	b := make([]byte, 7)
//	_, _ = rand.Read(b)
//	var salt = strconv.FormatInt(114514*int64(b[0]*114+b[6]*51+b[3]*41+b[5]*91+b[4]*98+b[1]*10), 16)
//	h := md5.New()
//	for i := 0; i < 17; i++ {
//		_, _ = io.WriteString(h, password+salt)
//		password = fmt.Sprintf("%x", h.Sum(nil))
//	}
//	return salt + "$" + password
//}
//
//func CheckPasswordHash(needCheck string, truePassword string) bool {
//	var truePasswordSplitArray = strings.Split(truePassword,"$")
//	var truePasswordHash = truePasswordSplitArray[1]
//	var salt = truePasswordSplitArray[0]
//	h := md5.New()
//	for i := 0; i < 17; i++ {
//		_, _ = io.WriteString(h, needCheck+salt)
//		needCheck = fmt.Sprintf("%x", h.Sum(nil))
//	}
//	return truePasswordHash == needCheck
//}
