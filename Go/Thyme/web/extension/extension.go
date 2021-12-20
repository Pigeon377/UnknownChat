package extension

import (
	"crypto/md5"
	"crypto/rand"
	"fmt"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"io"
	"strconv"
	"strings"
)

var (
	DB, err = gorm.Open(mysql.Open("root:3777777@tcp(127.0.0.1:3306)/thyme?charset=utf8mb4&parseTime=True&loc=Local"), &gorm.Config{})
)

func init() {
	if err != nil {
		fmt.Println(err)
	}
	return
}

func GeneratePasswordHash(password string) string {
	b := make([]byte, 7)
	_, _ = rand.Read(b)
	var salt = strconv.FormatInt(114514*int64(b[0]*114+b[6]*51+b[3]*41+b[5]*91+b[4]*98+b[1]*10), 16)
	h := md5.New()
	for i := 0; i < 17; i++ {
		_, _ = io.WriteString(h, password+salt)
		password = fmt.Sprintf("%x", h.Sum(nil))
	}
	return salt + "$" + password
}

func CheckPasswordHash(needCheck string, truePassword string) bool {
	var truePasswordSplitArray = strings.Split(truePassword,"$")
	var truePasswordHash = truePasswordSplitArray[1]
	var salt = truePasswordSplitArray[0]
	h := md5.New()
	for i := 0; i < 17; i++ {
		_, _ = io.WriteString(h, needCheck+salt)
		needCheck = fmt.Sprintf("%x", h.Sum(nil))
	}
	return truePasswordHash == needCheck
}
