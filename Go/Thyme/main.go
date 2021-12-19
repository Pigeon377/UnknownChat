package main

import (
	"Thyme/web"
	"fmt"
)

func main() {
	println(web.CheckPasswordHash("114514",web.GeneratePasswordHash("114514")))
	app, err := web.Init()
	if err != nil {
		fmt.Println(err)
		return
	}
	err1 := app.Run(":2333")
	if err1 != nil {
		fmt.Println(err1)
		return
	}
}
