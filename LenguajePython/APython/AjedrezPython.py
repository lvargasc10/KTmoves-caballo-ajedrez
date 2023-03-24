import turtle
import tkinter as tk
from turtle import *

KNIGHT_MOVES = [(2, 1), (1, 2), (-1, 2), (-2, 1), (-2, -1), (-1, -2), (1, -2), (2, -1)]


class KnightTour:
    def __init__(self, board_size):
        self.tuplas=[]
        self.board_size = board_size  # tuple
        self.board = []
        for i in range(board_size[0]):
            temp = []
            for j in range(board_size[1]):
                temp.append(0)
            self.board.append(temp) # empty cell
        self.move = 1

    def print_board(self):
        print('board:')
        for i in range(self.board_size[0]):
            print(self.board[i])

    def warnsdroff(self, start_pos, GUI=False):
        x_pos,  y_pos = start_pos
        self.board[x_pos][y_pos] = self.move

        if not GUI:
            while self.move <= self.board_size[0] * self.board_size[1]:
                self.move += 1
                next_pos = self.find_next_pos((x_pos, y_pos))
                if next_pos:
                    x_pos, y_pos = next_pos
                    self.board[x_pos][y_pos] = self.move
                else:
                    self.print_board()
                    return self.board
        else:
            if self.move <= self.board_size[0] * self.board_size[1]:
                self.move += 1
                next_pos = self.find_next_pos((x_pos, y_pos))
                return next_pos

    def find_next_pos(self, current_pos):
        empty_neighbours = self.find_neighbours(current_pos)
        if len(empty_neighbours) == 0:
            return
        least_neighbour = 8
        least_neighbour_pos = ()
        for neighbour in empty_neighbours:
            neighbours_of_neighbour = self.find_neighbours(pos=neighbour)
            if len(neighbours_of_neighbour) <= least_neighbour:
                least_neighbour = len(neighbours_of_neighbour)
                least_neighbour_pos = neighbour
        return least_neighbour_pos

    def prueba(self, start_pos, GUI=False):
        x_pos,y_pos = start_pos
        self.board[x_pos][y_pos] = self.move

        if not GUI:
            while self.move <= self.board_size[0] * self.board_size[1]:
                self.move += 1
                next_pos = self.find_next_pos((x_pos, y_pos))
                if next_pos:
                    x_pos,y_pos = next_pos
                    self.tuplas.append((((x_pos)*100)//2,((y_pos)*-100//2))) 
                    self.board[x_pos][y_pos] = self.move                    
                else:                    
                    return self.tuplas
        else:
            if self.move <= self.board_size[0] * self.board_size[1]:
                self.move += 1
                next_pos = self.find_next_pos((x_pos, y_pos))                
                return self.tuplas
            
    def find_neighbours(self, pos):
        neighbours = []
        for dx, dy in KNIGHT_MOVES:
            x = pos[0] + dx
            y = pos[1] + dy
            if 0 <= x < self.board_size[0] and 0 <= y < self.board_size[1] and self.board[x][y] == 0:
                neighbours.append((x, y))
        return neighbours
#-----------------------------------------------


def draw_board(dimension=8, x_coord=-16, y_coord=65, side=48):
    a = KnightTour((8, 8))   
    listaTuplas=a.prueba((2, 3)) 
    
    
    t.speed("fastest")
    t.pensize(5)
    parity = False
    t.hideturtle() 
    for i in range(dimension ** 2):
        if i % dimension == 0:
            y_coord -= side
            t.penup()
            t.setpos(x_coord, y_coord)
            t.pendown()
            parity = parity != (dimension % 2 == 0)  # logical XOR

        if parity:
            t.fillcolor("deepskyblue")
            t.begin_fill()
        if not parity:
            t.fillcolor("white")
            t.begin_fill()
            

        for _ in range(4):
            t.forward(side)
            t.right(90)

        if t.filling():
            t.end_fill()
        t.forward(side)
        parity = not parity
    t.penup()
    t.setpos(((2)*100)//2,((3)*-100//2))
    

def connectTheDots(coordinates, color="black"):
    
   
   
    t.stamp()
    t.speed("fastest")    
    t.color(color)    
    
    t.pensize(2)
    t.pendown()
    t.goto(((2)*100)//2,((3)*-100//2))
    for coordinate in coordinates:        
        t.goto(coordinate)
        t.stamp()
        t.pendown()  # redundant after first iteration

def doti():

  
    
    a = KnightTour((8, 8))
    b = KnightTour((8, 8))
    listaTuplas=a.prueba((2, 3))
    b.warnsdroff((2,3))
    b.prueba((2,3))
    print(listaTuplas)
    
    dots=listaTuplas
    
    connectTheDots(dots, "red")

def limpiar():
    for i in range((64*2)+1):
        t.undo()
        
def iniciar():
    doti()
    limpiar()
#----------------------------------
  
root = tk.Tk()
canvas = tk.Canvas(master = root, width = 600, height = 500)
canvas.pack()

screen = TurtleScreen(canvas)
#screen.bgcolor("grey")
screen.register_shape("horse.gif")
screen.bgcolor('lightgrey')

t = RawTurtle(screen)
t.shape("horse.gif")
t = turtle.RawTurtle(canvas)
t.shape("horse.gif")
#setworldcoordinates(self, llx, lly, urx, ury)
screen.setworldcoordinates(-80,-384,435,45)

tk.Button(master = root, text = "Salir",command=doti).pack(side=tk.LEFT)
tk.Button(master = root, text = "Iniciar", command = doti).pack(side = tk.BOTTOM)
tk.Button(master = root, text = "Limpiar", command = limpiar).pack(side = tk.BOTTOM)

draw_board(dimension=8, x_coord=-16, y_coord=65, side=48)
screen.mainloop()
